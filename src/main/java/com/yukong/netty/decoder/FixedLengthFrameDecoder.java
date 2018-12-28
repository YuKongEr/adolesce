package com.yukong.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author yukong
 * @date 2018/8/28
 * @description
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

    private final Integer frameLength;

    public FixedLengthFrameDecoder(Integer frameLength) {
        this.frameLength = frameLength;
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.readableBytes() > 0) {
            ByteBuf buf = byteBuf.readBytes(frameLength);
            list.add(buf);
        }
    }


    private String a = "a";

    public static void main(String[] args) throws MalformedURLException {
        FixedLengthFrameDecoder decoder  = new FixedLengthFrameDecoder(1);
        System.out.println(decoder.a);
        URL url = new URL("http://www.abc.com");
    }

}


