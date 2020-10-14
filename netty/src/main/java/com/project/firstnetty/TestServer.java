package com.project.firstnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.nio.NioServerSocketChannel;



import java.net.ServerSocket;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-13 09:16
 **/
public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        //定义两个线程组--相当于死循环
        NioEventLoopGroup bossgroup = new NioEventLoopGroup();
        NioEventLoopGroup workergroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossgroup,workergroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInit());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();//基于同步
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossgroup.shutdownGracefully();
            workergroup.shutdownGracefully();
        }





    }
}
