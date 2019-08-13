package com.yukong.netty.lecture;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author yukong
 * @date 2019-08-11 11:14
 */
@Slf4j
public class MyClient {

    private static final String DEFAULT_REMOTE_ADDRESS = "127.0.0.1";

    private static final Integer DEFAULT_REMOTE_PORT= 8989;

    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture future = bootstrap.channel(NioSocketChannel.class)
                    .group(bossGroup)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new MyClientHandler());
                        }
                    })
                    .connect(DEFAULT_REMOTE_ADDRESS, DEFAULT_REMOTE_PORT)
                    .sync();
            log.info("client 启动成功 请求远程地址【{}】", DEFAULT_REMOTE_ADDRESS + ":" + DEFAULT_REMOTE_PORT);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }

}
