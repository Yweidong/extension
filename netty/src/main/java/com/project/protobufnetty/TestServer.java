package com.project.protobufnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-14 14:41
 **/
public class TestServer {
    public static void main(String[] args) {
       EventLoopGroup bossgroup = new NioEventLoopGroup();
       EventLoopGroup workergroup = new NioEventLoopGroup();
       try {
           ServerBootstrap serverBootstrap = new ServerBootstrap();
           serverBootstrap.group(bossgroup,workergroup)
                            .channel(NioServerSocketChannel.class)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .childHandler(new TestServerInit());
           ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
           channelFuture.channel().closeFuture().sync();
       } catch (InterruptedException e) {
           e.printStackTrace();
       } finally {
           bossgroup.shutdownGracefully();
           workergroup.shutdownGracefully();
       }
    }
}
