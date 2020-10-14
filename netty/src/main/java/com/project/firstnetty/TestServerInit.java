package com.project.firstnetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;



/**
 * @program: extension
 * @description:  连接被创建后就会执行这个方法
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-13 09:24
 **/
public class TestServerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("TestServerHandler",new TestServerHandler());

    }
}
