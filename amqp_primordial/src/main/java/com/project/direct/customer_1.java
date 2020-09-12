package com.project.direct;

import com.project.util.amqpUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Auther: 杨伟栋
 * @Date: 2020/9/12 21:17
 * @Description: 1371690483@qq.com  发送到指定的routekey
 *
 */
public class customer_1 {
    public static void main(String[] args) {
        Connection connection = amqpUtils.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();//创建管道
            String queue = channel.queueDeclare().getQueue();//创建临时队列
            channel.exchangeDeclare("testdirect","direct");
            /*
             * @Author 杨伟栋
             * @Description  交换机和队列的绑定
             * @Date 2020/9/12 9:14 下午
             * @Param [args]
             * @return void
             **/
            channel.queueBind(queue,"testdirect","tt1");


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
