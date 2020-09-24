package com.project.Semaphore;

import java.util.concurrent.Semaphore;

/**
 * @program: extension
 * @description: 信号灯法，到达一定数量执行完毕后释放
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-24 10:49
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(()->{

                try {
                    semaphore.acquire();//抢占 当抢占满3后，之后的线程就会等待
                    System.out.println(Thread.currentThread().getName()+"抢占");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();//释放
                    System.out.println(Thread.currentThread().getName()+"释放");
                }
            },String.valueOf(i)).start();
        }
    }
}
