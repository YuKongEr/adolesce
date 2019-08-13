package com.yukong.netty.heart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yukong
 * @date 2019-08-09 16:58
 * 抽象心跳处理器
 */
@Slf4j
public abstract class AbstractHeartBeatHandler extends SimpleChannelInboundHandler<Packet> {

    /**
     * 处理器名称
     */
    private String handlerName;


    private int heartBeatCount = 0;

    public AbstractHeartBeatHandler(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, Packet packet) throws Exception {
        if (PacketType.PING_MSG.equals(packet.getType())) {
            log.info("【服务端】" + " get ping msg from " + context.channel().remoteAddress());

            sendPongMsg(context);
        } else if (PacketType.PONG_MSG.equals(packet.getType())){
            log.info("【客户端】" + " get pong msg from " + context.channel().remoteAddress());
        } else {
            handleData(context, packet);
        }
    }

    protected void sendPingMsg(ChannelHandlerContext context) {
        Packet pongPacket = new Packet();
        pongPacket.setContent("client send ping  heartbeat data...").setType(PacketType.PING_MSG);
        context.channel().writeAndFlush(pongPacket);
        heartBeatCount++;
    }

    private void sendPongMsg(ChannelHandlerContext context) {
        Packet pongPacket = new Packet();
        pongPacket.setContent("server response heartbeat data...").setType(PacketType.PONG_MSG);
        context.channel().writeAndFlush(pongPacket);
        heartBeatCount++;
    }

    protected abstract void handleData(ChannelHandlerContext channelHandlerContext, Packet packet);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("---" + ctx.channel().remoteAddress() + " is active---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("---" + ctx.channel().remoteAddress() + " is inactive---");
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
    }
}
