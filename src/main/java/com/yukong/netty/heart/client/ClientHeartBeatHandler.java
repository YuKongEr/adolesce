package com.yukong.netty.heart.client;

import com.yukong.netty.heart.AbstractHeartBeatHandler;
import com.yukong.netty.heart.Packet;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author yukong
 * @date 2019-08-13 11:22
 */
@Slf4j
public class ClientHeartBeatHandler extends AbstractHeartBeatHandler {

    private ClientBootstrap clientBootstrap;

    public ClientHeartBeatHandler(String handlerName, ClientBootstrap clientBootstrap) {
        super(handlerName);
        this.clientBootstrap = clientBootstrap;
    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, Packet packet) {
        log.info("【客户端】: {}", packet);
    }

    @Override
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        super.handleReaderIdle(ctx);
    }

    @Override
    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        super.handleWriterIdle(ctx);
    }

    @Override
    protected void handleAllIdle(ChannelHandlerContext ctx) {
        super.handleAllIdle(ctx);
   //     sendPingMsg(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().eventLoop().schedule(() -> {
            clientBootstrap.doConnect();
        }, 10, TimeUnit.SECONDS);
    }

}
