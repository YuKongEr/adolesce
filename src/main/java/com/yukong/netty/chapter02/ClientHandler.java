package com.yukong.netty.chapter02;

import lombok.extern.slf4j.Slf4j;

import javax.naming.ldap.SortKey;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author yukong
 * @date 2019-07-30 20:44
 */
@Slf4j
public class ClientHandler {

    private final Socket client;

    public static final int MAX_DATA_LEN = 1024;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    public void start() {
        log.info("新客户端接入");
        new Thread(() -> {
            doStart();
        }).start();
    }

    private void doStart() {
        try {
            InputStream inputStream = client.getInputStream();
            while (true) {
                byte[] data = new byte[MAX_DATA_LEN];
                int len;
                while((len = inputStream.read(data)) != -1) {
                    String message = new String(data, 0, len);
                    log.info("客户端传来消息: {}", message);
                    client.getOutputStream().write(data);
                }
            }
        } catch (IOException e) {
            log.error("读取客户端消息异常：{}", e);
        }
    }
}
