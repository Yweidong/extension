package com.project.protobufnetty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-14 15:18
 **/
public class TestClient {
    public static void main(String[] args) {
       EventLoopGroup eventExecutors = new NioEventLoopGroup();
       try {
           Bootstrap bootstrap = new Bootstrap();
           bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                    .handler(new TestClinetProInit());
           ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
           channelFuture.channel().closeFuture().sync();
       } catch (InterruptedException e) {
           e.printStackTrace();
       } finally {
           eventExecutors.shutdownGracefully();
       }
    }
}
