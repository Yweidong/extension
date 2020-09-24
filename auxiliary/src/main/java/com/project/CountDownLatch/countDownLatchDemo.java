package com.project.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @program: extension
 * @description: 减法计数器
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-24 10:39
 **/
public class countDownLatchDemo {


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();//阻塞等待

        System.out.println("end");
    }
}
