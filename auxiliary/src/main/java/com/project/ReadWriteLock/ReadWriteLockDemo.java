package com.project.ReadWriteLock;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: extension
 * @description: 读写锁
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-24 11:13
 *      独占锁  （写锁）
 *      共享锁   (读锁)
 *
 **/
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {

            int finalI = i;
            new Thread(()->{
                myCache.put(Thread.currentThread().getName(), finalI);
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {

            int finalI = i;
            new Thread(()->{
                myCache.read(Thread.currentThread().getName());
            },String.valueOf(i)).start();
        }
    }
}

class MyCache{

    private volatile HashMap<String,Object> hashMap = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    //写数据
    public void put(String key,Object value) {
        readWriteLock.writeLock().lock();//写锁
        try {
            System.out.println(key+"写入数据"+value+"😂😘");
            hashMap.put(key,value);
            System.out.println(key+"写入成功");
        }finally {
            readWriteLock.writeLock().unlock();
        }

    }

    //读数据
    public void read(String key) {
        readWriteLock.readLock().lock();//读锁
        try {
            hashMap.get(key);
            System.out.println(key+"读取成功");
        }finally {
            readWriteLock.readLock().unlock();
        }

    }

}
