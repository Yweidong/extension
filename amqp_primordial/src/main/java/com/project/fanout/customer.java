package com.project.fanout;

import com.project.util.amqpUtils;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQImpl;

import java.io.IOException;
import java.util.Queue;

/**
 * @Auther: 杨伟栋
 * @Date: 2020/9/12 21:05
 * @Description: 1371690483@qq.com
 */
public class customer {
    public static void main(String[] args) {
        Connection connection = amqpUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();//创建管道
            String queue = channel.queueDeclare().getQueue();//创建临时队列
            channel.exchangeDeclare("testfanout","fanout");
            /*
             * @Author 杨伟栋
             * @Description  交换机和队列的绑定
             * @Date 2020/9/12 9:14 下午
             * @Param [args] arg0 队列  arg1  交换机名称  arg2  routeKey
             * @return void
             **/
            channel.queueBind(queue,"testfanout","");


            Channel finalChannel = channel;
            // 设置客户端最多接受未被ack消息的个数
            finalChannel.basicQos(64);
            channel.basicConsume(queue,new DefaultConsumer(finalChannel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(new String(body));
                    finalChannel.basicAck(envelope.getDeliveryTag(),false);//手动确认消息
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
