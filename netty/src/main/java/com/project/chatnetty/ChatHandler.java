package com.project.chatnetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-13 14:13
 **/
public class ChatHandler extends SimpleChannelInboundHandler<String> {
    //保存所有连接的channel对象
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if(ch!=channel) {
                //不是当前客户端发送的消息
                ch.writeAndFlush(ch.remoteAddress()+": "+s+"\n");
            }else {
                ch.writeAndFlush("自己: "+s+"\n");
            }
        });
    }

    /**
     *回调方法，当有客户端<连接>的时候就会调用这个方法
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //有新连接就广播
        channelGroup.writeAndFlush("[服务器]"+channel.remoteAddress()+"加入\n");
        channelGroup.add(channel);
    }
    /**
     *回调方法，当有客户端<关闭>的时候就会调用这个方法
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器]"+channel.remoteAddress()+"断开\n");
//        channelGroup.remove(channel);  netty自动移除
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"离开");
    }

    /**
     *回调方法，当有客户端<异常>的时候就会调用这个方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}
