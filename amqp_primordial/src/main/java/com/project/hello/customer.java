package com.project.hello;

import com.project.util.amqpUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Auther: 杨伟栋
 * @Date: 2020/9/12 20:49
 * @Description: 1371690483@qq.com
 */
public class customer {
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
            Channel finalChannel = channel;
            // 设置客户端最多接受未被ack消息的个数
            finalChannel.basicQos(64);
            channel.basicConsume("hello",new DefaultConsumer(finalChannel) {
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
