package com.yukong.netty.compare;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: yukong
 * @date: 2018/12/19 10:01
 */
public class NettyNioServer {

    public static void main(String[] args) throws InterruptedException {
        final ByteBuf buf = Unpooled.unmodifiableBuffer(Unpooled.copiedBuffer("Hi\n".getBytes()));
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group,group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(7070))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        channelFuture.channel().closeFuture().sync();
    }

}
