package com.yukong.netty.compare;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: yukong
 * @date: 2018/12/18 16:52
 */
public class JdkNioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel channel  = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6969));
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int length = channel.read(byteBuffer);
        if (length > 0) {
            byteBuffer.flip();
            //根据缓冲区可读字节数创建字节数组
            byte[] bytes = new byte[byteBuffer.remaining()];
            // 复制至新的缓冲字节流
            byteBuffer.get(bytes);
            System.out.println(new String(bytes, "UTF-8"));
        }
    }

}
