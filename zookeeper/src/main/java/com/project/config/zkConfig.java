package com.project.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-12 14:56
 **/
@Configuration
public class zkConfig {
    private static final Logger logger = LoggerFactory.getLogger(zkConfig.class);
    @Bean
    public ZooKeeper zkClient() {

        ZooKeeper zooKeeper = null;
        try {
            //计数器
            CountDownLatch countDownLatch = new CountDownLatch(1);
            //zookeeper连接时异步操作
            zooKeeper  = new ZooKeeper("106.15.251.237", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        logger.info("连接成功");
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();//阻塞等待
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }
}
