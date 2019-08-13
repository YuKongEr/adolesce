package com.yukong.netty.chapter02;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author yukong
 * @date 2019-07-30 20:52
 */
@Slf4j
public class Client {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8000;

    private static final int SLEEP_TIME = 5000;


    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(HOST, PORT);
        new Thread(() -> {
            log.info("客户端启动成功");
            while (true) {
                try {
                    String message = "hello world";
                    log.info("客户端发送数据：{}", message);
                    socket.getOutputStream().write(message.getBytes());
                } catch (Exception e) {
                    log.error("客户端发送数据失败：{}", e);
                }
                sleep();

            }
        }).start();
    }

    private static void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            log.error("线程错误");
        }
    }

}


