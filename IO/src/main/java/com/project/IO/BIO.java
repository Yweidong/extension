package com.project.IO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: extension
 * @description: 阻塞式  每一个连接一个线程
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-12 10:51
 **/
public class BIO {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);//监听端口
        while (true) {
            Socket accept = serverSocket.accept();//获取链接的客户端，阻塞

            new Thread(()->{
                while (true) {
                    System.out.println(Thread.currentThread().getName());
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    byte[] bytes = new byte[1024];
                    try {
                        accept.getInputStream().read(bytes);//将数据读取出来
                        String res = new String(bytes);
                        System.out.println(res);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
