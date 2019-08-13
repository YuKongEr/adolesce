package com.yukong.netty.chapter02;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yukong
 * @date 2019-07-30 20:39
 */
@Slf4j
public class Server {


    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            log.info("服务端启动成功，端口：{}", port);
        } catch (IOException e) {
            log.error("服务端启动失败：{}", e);
        }
    }

    public void start() {
        new Thread(() -> {
            doStart();
        }).start();
    }

    private void doStart() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            } catch (IOException e) {
                log.error("服务端异常：{}", e);
            }
        }
    }


}
