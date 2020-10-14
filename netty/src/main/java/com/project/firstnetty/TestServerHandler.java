package com.project.firstnetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @program: extension
 * @description:  自定义handler  读取客户端发过来的请求，并返回响应
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-13 09:30
 **/
public class TestServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if(httpObject instanceof HttpRequest) {
            String name = ((HttpRequest) httpObject).method().name();//请求方式
            System.out.println(name);
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            //构造http响应
            FullHttpResponse fullHttpResponse= new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, byteBuf);
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");//设置响应头
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            channelHandlerContext.writeAndFlush(fullHttpResponse);//返回响应到客户端
        }


    }
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("regist");
        ctx.fireChannelRegistered();
    }
}
