package com.yukong.netty.heart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author yukong
 * @date 2019-08-09 17:16
 * 字节转数据包 解码器
 */
public class ByteToPacketDecoder extends ByteToMessageDecoder {

    private final static Integer BASE_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
       if(in.readableBytes() > BASE_LENGTH) {
           in.markReaderIndex();
           Integer length = in.readInt();
           if(in.readableBytes() >= length) {
               Packet packet = new Packet();
               Character type = in.readChar();
               byte[] bytes = new byte[length - 2];
               in.readBytes(bytes);
               String content = new String(bytes, CharsetUtil.UTF_8);
               packet.setLength(length);
               packet.setType(type);
               packet.setContent(content);
               out.add(packet);
           } else {
               in.resetReaderIndex();
           }
       }
    }
}
