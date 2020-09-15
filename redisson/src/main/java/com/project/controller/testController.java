package com.project.controller;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-15 08:54
 **/
@RestController
@RequestMapping("api/v1")
public class testController {
    @Autowired
    RedissonClient redissonClient;
    private final String _lock = "_lock";
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getCurrentDate(){
        return sdf.format(new Date());
    }
    @GetMapping("/test")
    public String testlock(@RequestParam("name") String name) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                RLock lock = redissonClient.getLock(_lock);//获取锁
                lock.lock();//锁住

                System.out.println(getCurrentDate()+" "+name+" begin...");
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(getCurrentDate()+" "+name+" "+i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(getCurrentDate()+" "+name+" end...");

                lock.unlock();//释放锁
            }
        }).start();

        return "testlock";
    }
}
