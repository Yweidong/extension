package com.project.protobufnetty.client;

import com.project.protobufnetty.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-14 15:22
 **/
public class TestClientProHandler extends SimpleChannelInboundHandler<MyDataInfo.Mymessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.Mymessage mymessage) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int i = new Random().nextInt(3);
        MyDataInfo.Mymessage mymessage = null;
        if(i == 0) {
            mymessage = MyDataInfo.Mymessage.newBuilder().setMessageType(MyDataInfo.Mymessage.MessageType.PersonType)
                            .setPerson(MyDataInfo.Person.newBuilder()
                                    .setName("测试")
                                    .setId(13)
                                    .setAddress("南京")
                                    .build()).build();
        }else if(i==1) {
            mymessage = MyDataInfo.Mymessage.newBuilder().setMessageType(MyDataInfo.Mymessage.MessageType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder()
                            .setName("猫")
                            .setAge(12)
                            .build()).build();
        }else {
            mymessage = MyDataInfo.Mymessage.newBuilder().setMessageType(MyDataInfo.Mymessage.MessageType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder()
                            .setName("猫")
                            .setSex("男")
                            .build()).build();
        }


        ctx.channel().writeAndFlush(mymessage);

    }
}
