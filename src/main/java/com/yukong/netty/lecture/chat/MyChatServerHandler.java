package com.yukong.netty.lecture.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author yukong
 * @date 2019-08-11 12:46
 */
@Slf4j
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 保存channel
     */
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if(ch != channel) {
                ch.writeAndFlush("【" + ch.remoteAddress() + "】发送的消息" + msg + "\r\n");
            } else {
                channel.writeAndFlush("【自己】发送的消息" + msg + "\r\n");

            }
        });
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "离开\n");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("【服务器】 - " + channel.remoteAddress() + "上线\n");

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("【服务器】 - " + channel.remoteAddress() + "下线\n");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
