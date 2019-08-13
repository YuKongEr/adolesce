package com.yukong.netty.heart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author yukong
 * @date 2019-08-13 10:40
 */
public class PacketToByteEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        out.writeInt(packet.getLength());
        out.writeChar(packet.getType());
        out.writeBytes(packet.getContent().getBytes(CharsetUtil.UTF_8));
    }
}
