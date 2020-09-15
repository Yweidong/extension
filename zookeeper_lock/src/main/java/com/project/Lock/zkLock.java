package com.project.Lock;

import com.project.config.zkConfig;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @program: extension
 * @description:  zookeeper 锁
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-15 16:46
 **/
public class zkLock implements Lock {
//    @Autowired
//    ZooKeeper zkClient;
    private static final ZooKeeper zkClient = new zkConfig().zkClient();

    private static final Logger logger = LoggerFactory.getLogger(zkLock.class);
    private static final String path = "/LOCK";
    //创建就计数器
    private static final CountDownLatch cdl = new CountDownLatch(1);
    //当前节点
    private  String currectNode =null;
    public zkLock() {
        try {
            if(zkClient.exists(path,false) == null) {
                //创建锁根节点
                    zkClient.create(path,new byte[10], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        boolean b = tryLock();
        if(b) {
            lock();
        }else {
            tryLock();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }



    @Override
    public boolean tryLock() {
        try{
            if(currectNode == null) {
                //创建临时有序节点

                String s = zkClient.create(path + '/', "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                System.out.println(s);

            }

            //获取所有的子节点
            List<String> children = zkClient.getChildren(path, false);
            Collections.sort(children);//所有节点排序
//            children.forEach(s -> {
//                System.out.println(s);
//            });
            if(currectNode.equals(path+'/'+children.get(0))) {
                    /**
                     *
                     *  当前节点就是第一个，直接返回
                     */
                    return true;
            }else {
                //监听前一个节点
                //获取当前节点的下标
                Integer index = children.indexOf(currectNode.substring(6));

                Stat stat = zkClient.exists(path + '/' + children.get(index - 1), new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        //监听节点是否删除
                        if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                            cdl.countDown();//放行
                        }
                    }
                });
                if(stat == null) {
                    currectNode = children.get(index);
                    return true;
                }else {
                    cdl.await();//阻塞
                    return false;
                }

            }



        }catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        try {
            zkClient.delete(path+'/'+currectNode,-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) {
        zkLock zkLock = new zkLock();
        zkLock.tryLock();
    }
}
