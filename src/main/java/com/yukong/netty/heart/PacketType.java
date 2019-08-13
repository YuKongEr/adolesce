package com.yukong.netty.heart;

/**
 * @author yukong
 * @date 2019-08-09 17:11
 */
public interface PacketType {

    /**
     * 心跳请求包
     */
    Character PING_MSG = '1';

    /**
     * 心跳响应包
     */
    Character PONG_MSG = '2';

    /**
     * 消息包
     */
    Character MSG = '0';


}
