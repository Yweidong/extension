package com.project.Lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * @Auther: 杨伟栋
 * @Date: 2020/9/15 20:32
 * @Description: 1371690483@qq.com
 */
public class testLock {

    private static final Lock lock = new zkLock();

    //创建就计数器
    private static final CountDownLatch cdl = new CountDownLatch(10);
   int count = 0;
    public static void main(String[] args) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                lock.lock();//锁住


                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                lock.unlock();//释放锁
            }
        }).start();

    }



}
