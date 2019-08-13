package com.yukong.netty.heart.server;

import com.yukong.netty.heart.AbstractHeartBeatHandler;
import com.yukong.netty.heart.Packet;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yukong
 * @date 2019-08-13 11:27
 */
@Slf4j
public class ServerHeartBeatHandler extends AbstractHeartBeatHandler {
    public ServerHeartBeatHandler(String handlerName) {
        super(handlerName);
    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, Packet packet) {
        log.info("【服务端】: {}", packet);
    }


    @Override
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        log.info("【服务端】没有接到心跳包，关闭channel = {}", ctx.channel().id().asLongText());
        ctx.close();
    }
}
