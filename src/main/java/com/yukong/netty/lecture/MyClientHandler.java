package com.yukong.netty.lecture;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author yukong
 * @date 2019-08-11 11:20
 */
@Slf4j
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("received form:[{}], msg = {}", ctx.channel().remoteAddress(), msg);
        String content = "client send " + LocalDateTime.now();
        ctx.writeAndFlush(content);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("connect to Server");
        super.channelActive(ctx);
    }
}
