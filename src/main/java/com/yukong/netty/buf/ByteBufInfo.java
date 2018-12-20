package com.yukong.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author: yukong
 * @date: 2018/12/19 14:16
 */
public class ByteBufInfo {

    public static void main(String[] args) {
        // 创建 ByteBuf 三种方式
        ByteBuf buf = Unpooled.copiedBuffer(new String().getBytes());
    }

}
