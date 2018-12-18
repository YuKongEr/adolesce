package com.yukong.netty.sample.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author yukong
 * @date 2018/8/22
 * @description socket客户端
 */
public class MySocketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(MySocketServer.HOST, MySocketServer.PORT_NUMBER);
        String response, request;
        response = "helloyukong!";
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        // 注意一定用println  用print 服务端会接受不到消息
        out.println(response);
        System.out.println("send:" + response);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        while ((request = in.readLine()) != null) {
            if (MySocketServer.END_FLAG.equals(request)) {
                break;
            }
            System.out.println("received:" + request);
            response = request.toLowerCase();
            out.print(response);

        }
    }

}
