package com.project.IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: extension
 * @description: 非阻塞
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-10-12 11:07
 **/
public class NIO {
    private static ServerSocketChannel serverSocketChannel;
    //选择器
    private static Selector selector;//多路复用器
    public static void main(String[] args) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//切换NIO非阻塞模式
        serverSocketChannel.bind(new InetSocketAddress(8080));//绑定端口

        selector = Selector.open();//选择器打开
        /**
         * @Param selector 多路复用器
         * @Param  OP_ACCEPT  对新建立的连接感兴趣
         */
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select(1000);//如果有网络连接直接返回，没有就等待时间（1秒）
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println(selectionKeys);
            Iterator<SelectionKey> iterator = selectionKeys.iterator();//迭代器
            while(iterator.hasNext()) {//遍历处理每个事件
                SelectionKey res = iterator.next();
                if(res.isAcceptable()) {//判断是否是 OP_ACCEPT连接
                    //如果有新连接就开始建立连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);//非阻塞模式
                    socketChannel.register(selector,SelectionKey.OP_READ);//将这个连接的OP_READ事件继续注册到选择器中
                }else if(res.isReadable()) {//某一个连接有数据传输
                    SocketChannel socketChannel = (SocketChannel)res.channel();//获取连接对象
                    res.cancel();//取消连接监听，当前有线程在处理，无需监听
                    /**
                     *此处可用线程池
                     *
                     */
                    new Thread(()->{

                        try {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                            socketChannel.read(byteBuffer);
                            byteBuffer.flip();//切换成读取的模式
                            String ret = new String(byteBuffer.array());
                            System.out.println(ret);

                            //读取
                            String s = "hello word";
                            int write = socketChannel.write(ByteBuffer.wrap(s.getBytes()));

                            //处理完毕后，需保持连接，继续交给selector监听
                            socketChannel.register(selector,SelectionKey.OP_READ);
                        } catch (IOException e) {
                            System.out.println("连接已关闭");
//                            e.printStackTrace();
                        }
                    }).start();
                }
                iterator.remove();//将这个对象给删除掉
            }
            System.out.println(selector);
            selector.selectNow();//过滤掉无用的事件
        }


    }
}
