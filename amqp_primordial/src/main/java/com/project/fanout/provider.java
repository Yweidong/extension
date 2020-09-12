package com.project.fanout;

import com.project.util.amqpUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * @Auther: 杨伟栋
 * @Date: 2020/9/12 21:02
 * @Description: 1371690483@qq.com  广播
 */
public class provider {
    public static void main(String[] args) {
        Connection connection = amqpUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();//创建管道
            channel.exchangeDeclare("testfanout","fanout");
            channel.basicPublish("testfanout","",MessageProperties.PERSISTENT_TEXT_PLAIN,"测试".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            amqpUtils.closeConnection(connection,channel);
        }
    }
}
