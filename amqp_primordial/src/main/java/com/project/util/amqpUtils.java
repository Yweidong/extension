package com.project.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: 杨伟栋
 * @Date: 2020/9/12 20:31
 * @Description: 1371690483@qq.com
 */
public class amqpUtils {

    private static final ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("106.15.251.237");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("ems");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");


    }

    public static Connection getConnection() {

        try {
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Connection conn, Channel channel) {
        try {
            if (conn!=null){

                conn.close();

            }
            if(channel!=null) {
                channel.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
