package com.project.utils;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @program: extension
 * @description: zk工具
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-12 15:13
 **/
public class zkUtils {
    private static final Logger logger = LoggerFactory.getLogger(zkUtils.class);
    @Autowired
    ZooKeeper zkClient;
    /**
     *判断节点是否存在
     * @param path 目录节点
     * @param watch 创建Zookeeper实例时指定的watcher
     */
    public Stat existsBoolen (String path, Boolean watch) {
        try {
            return zkClient.exists(path,watch);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 同步创建节点
     * @param path 节点目录
     * @param data 数据
     */
    public boolean create(String path,String data) {
        try {
            /**
             * false 不适用默认的watcher 监听，，即创建zk实例时设置的watcher
             */
            if(existsBoolen(path,false) == null) {
                /**
                 * OPEN_ACL_UNSAFE：完全开放
                 * CREATOR_ALL_ACL：创建该znode的连接拥有所有权限
                 * READ_ACL_UNSAFE：所有的客户端都可读
                 *
                 */


                /**
                 *  1.PERSISTENT--持久型
                 * 2.PERSISTENT_SEQUENTIAL--持久顺序型
                 * 3.EPHEMERAL--临时型
                 * 4.EPHEMERAL_SEQUENTIAL--临时顺序型
                 *  5.PERSISTENT_WITH_TTL --再一个ttl时间段内，当前节点再一段时间内没有更新值或者添加子节点，会自动删除
                 */
                zkClient.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                return true;
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 同步创建自定义acl节点
     */
    public boolean createCuntom(String path, String data, ArrayList<ACL> acls) {
        try {
            /**
             * false 不适用默认的watcher 监听，，即创建zk实例时设置的watcher
             */
            if(existsBoolen(path,false) == null) {

                zkClient.create(path,data.getBytes(),acls,CreateMode.PERSISTENT);
                return true;
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * 异步创建
     *
     */
    public boolean createAsy(String path,String data) {
        try {
            if(existsBoolen(path,false) == null) {
                zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.Create2Callback() {
                    @Override
                    public void processResult(int i, String s, Object o, String s1, Stat stat) {
                        System.out.println(i);//创建成功返回0
                        System.out.println(s); //节点名称
                        System.out.println(s1);//节点名称
                        System.out.println(stat);//节点的信息  如：创建节点的事务id，时间等
                    }
                },"hello");
                return true;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
