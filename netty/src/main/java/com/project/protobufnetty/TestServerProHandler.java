package com.project.protobufnetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-14 14:52
 **/
public class TestServerProHandler extends SimpleChannelInboundHandler<MyDataInfo.Mymessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.Mymessage mymessage) throws Exception {
        MyDataInfo.Mymessage.MessageType messageType = mymessage.getMessageType();
        if(messageType == MyDataInfo.Mymessage.MessageType.PersonType) {
            MyDataInfo.Person person = mymessage.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getId());
            System.out.println(person.getAddress());

        }else if(messageType == MyDataInfo.Mymessage.MessageType.DogType) {
            MyDataInfo.Dog dog = mymessage.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        }else {
            MyDataInfo.Cat cat = mymessage.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getSex());

        }


    }
}
