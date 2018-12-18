package com.yukong.netty.sample.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author yukong
 * @date 2018/8/23
 * @description 引导服务器
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(8899).start();
    }

    public void start() throws Exception{
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 在当前ChannelInitializer容器最后添加 ChannelHandler
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步绑定服务器，并且调用sync阻塞等待直到绑定完成
            ChannelFuture future = bootstrap.bind().sync();
            // 获取Channel的closeFuture 并且阻塞到当前线程完成
            future.channel().closeFuture().sync();
        } finally {
            // 关闭group 并且释放所有资源
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
