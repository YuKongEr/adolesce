package com.yukong.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author yukong
 * @date 2019-08-06 22:02
 */
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // websocket 基于http协议， 所以需要http协议编解码器
        pipeline.addLast(new HttpServerCodec())
                // 对写大数据流的支持
                .addLast(new ChunkedWriteHandler())
                // 对HttpMessage进行聚合, 聚合成FullHttpRequest或者是FullHttpResponse
                .addLast(new HttpObjectAggregator(1024 * 64))
                // webSocket 服务器处理的协议 用于指定给客户端连接访问的路由/ws
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                .addLast(new ChatHandler());
    }
}
