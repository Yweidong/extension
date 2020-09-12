package com.project.hello;

import com.project.util.amqpUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * @Auther: 杨伟栋
 * @Date: 2020/9/12 20:40
 * @Description: 1371690483@qq.com
 *
 * 单播  hello 模式  ，，消费者
 */
public class provider {
    public static void main(String[] args) {
        Connection connection = amqpUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();//创建管道
            /*
             * @Author 杨伟栋
             * @Description
             * @Date 2020/9/12 8:45 下午
             * @Param args0 队列名称  arg1 消息是否持久化  arg2 是否独占通道, arg3是否自动删除通道
             * @return void
             **/
            channel.queueDeclare("hello",true,false,false,null);
            channel.basicPublish("","hello", MessageProperties.PERSISTENT_TEXT_PLAIN,"hello".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            amqpUtils.closeConnection(connection,channel);
        }
    }
}
