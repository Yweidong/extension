package com.project.websocketnetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-14 09:34
 *
 * 新连接005056fffec00008-00005274-00000001-035b7e542a7a622d-7ca42feb
 * 断开连接005056fffec00008-00005274-00000001-035b7e542a7a622d-7ca42feb
 * 新连接005056fffec00008-00005274-00000002-231efe15ba7bf0ea-067cf887
 * 断开连接005056fffec00008-00005274-00000002-231efe15ba7bf0ea-067cf887
 **/
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("消息："+textWebSocketFrame.text());
        ctx.writeAndFlush(new TextWebSocketFrame("123"));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /**
         *
         *asLongText()  全局唯一
         *asShortText()  全局不唯一
         */
        System.out.println("新连接"+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开连接"+ctx.channel().id().asLongText());
    }
}
