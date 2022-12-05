package io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: xjjiang
 * @Data: 2022/11/23 17:47
 * @Description: NIO非阻塞通信下的入门案例：服务端开发
 */
public class Server {

    public static void main(String[] args) throws Exception {
        //1.获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //2.切换为非阻塞模式
        ssChannel.configureBlocking(false);
        //3.绑定连接的端口
        ssChannel.bind(new InetSocketAddress(9999));
        //4.获取选择器Selector
        Selector selector = Selector.open();
        //5.蒋通道注册刀选择器上去，并且开始指定监听器接收事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6.使用Selector选择器轮询已经就绪好的事件
        while (selector.select()>0){
            //7.获取选择器中所有注册的通道中已经就绪好的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            //8.开始遍历这些准备好的事件
            while (it.hasNext()){
                SelectionKey sk = it.next(); //提取当前这个事件
                //9.判断这个事件具体是什么
                if(sk.isAcceptable()){
                    //10.直接获取当前接入的客户端通道
                    SocketChannel socketChannel = ssChannel.accept();
                    //11.切换成非阻塞模式
                    socketChannel.configureBlocking(false);
                    //12.将本客户端通道注册到选择器
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if(sk.isReadable()){
                    //13.获取当前选择器上的读就绪事件
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
                    //14.读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(buffer))>0){
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }
                }
                it.remove();
            }
        }
    }

}
