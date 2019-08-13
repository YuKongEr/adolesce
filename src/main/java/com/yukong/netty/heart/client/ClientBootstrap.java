package com.yukong.netty.heart.client;

import com.yukong.netty.heart.ByteToPacketDecoder;
import com.yukong.netty.heart.Packet;
import com.yukong.netty.heart.PacketToByteEncoder;
import com.yukong.netty.heart.PacketType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yukong
 * @date 2019-08-13 11:29
 */
@Slf4j
public class ClientBootstrap {

    private NioEventLoopGroup workGroup = new NioEventLoopGroup(4);
    private Channel channel;
    private Bootstrap bootstrap;

    public ClientBootstrap() {
    }

    public static void main(String[] args) throws Exception {
        ClientBootstrap clientBootstrap = new ClientBootstrap();
        clientBootstrap.start();
        TimeUnit.SECONDS.sleep(2);
        clientBootstrap.senData();
    }

    public void start() throws Exception {
        workGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 0, 5));
                        pipeline.addLast(new ByteToPacketDecoder());
                        pipeline.addLast(new PacketToByteEncoder());
                        pipeline.addLast(new ClientHeartBeatHandler("client 1", ClientBootstrap.this));
                    }
                });

        doConnect();

    }

    public void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }

        ChannelFuture future = bootstrap.remoteAddress("127.0.0.1", 12345).connect();
        future.addListener((ChannelFuture futureListener) -> {
            if (futureListener.isSuccess()) {
                channel = futureListener.channel();
                log.info("Connect to server successful!");
            } else {
                log.info("Connect to server fail, after 10s reconnect");
                futureListener.channel().eventLoop().schedule(() -> {
                    doConnect();
                }, 10, TimeUnit.SECONDS);
            }
        });
    }

    private void senData() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            String content = "client msg " + i;
            Packet packet = new Packet();
            packet.setType(PacketType.MSG).setContent(content);
            channel.writeAndFlush(packet);
            Thread.sleep(20000);
        }
    }

}
