package com.project.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-14 14:19
 **/
public class ProtoBufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        //构建student对象
        DataInfo.Student student = DataInfo.Student.newBuilder()
                                        .setName("张三")
                                        .setId(12)
                                        .setAddress("南京")
                                        .build();
        byte[] bytes = student.toByteArray();//转换成字节数组，以便可以在网络上传输
        DataInfo.Student student1 = DataInfo.Student.parseFrom(bytes);
        System.out.println(student1.getName());
        System.out.println(student1.getId());
        System.out.println(student1.getAddress());
    }
}
