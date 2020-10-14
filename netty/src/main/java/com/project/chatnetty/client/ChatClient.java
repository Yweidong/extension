package com.project.chatnetty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-13 14:46
 **/
public class ChatClient {
    public static void main(String[] args) {
      EventLoopGroup loopGroup = new NioEventLoopGroup();
      try {
          Bootstrap bootstrap = new Bootstrap();
          bootstrap.group(loopGroup).channel(NioSocketChannel.class)
                                    .handler(new ChatClientInit());
//          获取channel 向服务端发送数据
          Channel channel= bootstrap.connect("localhost", 8080).sync().channel();
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
          for (;;) {
              channel.writeAndFlush(bufferedReader.readLine()+"\r\n");
          }

      } catch (InterruptedException | IOException e) {
          e.printStackTrace();
      } finally {
          loopGroup.shutdownGracefully();
      }

    }
}
