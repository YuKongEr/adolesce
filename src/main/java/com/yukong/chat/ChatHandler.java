package com.yukong.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author yukong
 * @date 2019-08-06 22:11
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final ChannelGroup client = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 客户端连接服务端之后
     * 获取客户端的channel 并且放到ChannelGroup中去进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        client.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved 并且放到ChannelGroup中去进行管理会自动移除客户端的Channel
        // client.remove(ctx.channel());
        log.info("客户端断开 channel 长id = {}, 短id = {}", ctx.channel().id().asLongText(), ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输的消息
        String content = msg.text();
        log.info("接收到的数据：{}", content);
        client.forEach(channel -> {
            channel.writeAndFlush(new TextWebSocketFrame("【服务器接收到消息 ：】" + LocalDateTime.now() + "接收到消息，消息为：" + content));
        });
    }
}

