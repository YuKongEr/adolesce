package com.yukong.netty.heart.server;

import com.yukong.netty.heart.ByteToPacketDecoder;
import com.yukong.netty.heart.Packet;
import com.yukong.netty.heart.PacketToByteEncoder;
import com.yukong.netty.heart.PacketType;
import com.yukong.netty.heart.client.ClientHeartBeatHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Random;

/**
 * @author yukong
 * @date 2019-08-13 11:35
 */
public class ServerBootStrap {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Random random = new Random(System.currentTimeMillis());
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(10 ,0, 0));
                            pipeline.addLast(new ByteToPacketDecoder());
                            pipeline.addLast(new PacketToByteEncoder());
                            pipeline.addLast(new ServerHeartBeatHandler("server 1"));
                        }
                    });

            Channel ch = bootstrap.bind(12345).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
