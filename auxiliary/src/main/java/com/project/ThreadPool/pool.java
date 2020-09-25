package com.project.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: extension
 * @description: 线程池
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-25 08:27
 *
 *
 *  =========== 拒绝策略==================
 *   new ThreadPoolExecutor.AbortPolicy()  一旦超过线程的数量（max+queue）就会抛异常
 *  new ThreadPoolExecutor.CallerRunsPolicy() 一旦超过线程的数量（max+queue） 就会返回给执行这个逻辑的那个线程继续执行
 *  new ThreadPoolExecutor.DiscardPolicy() 一旦超过线程的数量（max+queue） 直接抛弃后来的线程
 *  new ThreadPoolExecutor.DiscardOldestPolicy()  一旦超过线程的数量（max+queue） 会和之前的线程去竞争
 *
 *
 *
 *  ================最大线程数量=====================
 *
 *  1、 根据cpu的核数，让cpu达到最大利用率  （cpu密集型）
 *
 *  2、根据I/O 判断程序中执行时间长的I/O 一般大于这个的两倍数量  （I/O密集型）
 **/
public class pool {

    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());//获取cpu的核数
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,//核心线程数量
                5,//最大线程数量
                3, //超时等待时间,超过三秒就释放
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),//阻塞队列--双端duilie
                Executors.defaultThreadFactory(),//一般就这个固定
//                new ThreadPoolExecutor.AbortPolicy()//拒绝策略
//                new ThreadPoolExecutor.CallerRunsPolicy()
//                new ThreadPoolExecutor.DiscardPolicy()
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );


        try {
            for (int i = 1; i <=9; i++) {
                int finalI = i;
                threadPoolExecutor.execute(()->{
                    System.out.println(Thread.currentThread().getName()+ "----"+finalI);
                });
            }
        }finally {
            threadPoolExecutor.shutdown();
        }

    }

}
