package com.yukong.netty.chapter02;

/**
 * @author yukong
 * @date 2019-07-30 20:46
 */
public class ServerBoot {

    public static void main(String[] args) {
        new Server(8000).start();
    }
}
