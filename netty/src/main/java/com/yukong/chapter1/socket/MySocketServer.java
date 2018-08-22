package com.yukong.chapter1.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yukong
 * @date 2018/8/22
 * @description 自定义SocketServer
 */
public class MySocketServer {


    /**
     * socket监听服务器
     */
    public final static String HOST = "127.0.0.1";
    /**
     * socket监听端口
     */
    public final static Integer PORT_NUMBER = 6789;

    /**
     * socket连接介绍标识
     */
    public final static String END_FLAG = "DONE";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        // 阻塞方法 没有接到socket消息之前 下面代码不会执行，一直阻塞
        System.out.println("server starter waiting...");
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        while ((request = in.readLine()) != null) {
            if (END_FLAG.equals(request)) {
                break;
            }
            System.out.println("received:" + request);
            response = new StringBuffer(request).reverse().toString();
            // 服务端相应
            out.println(response);
        }
    }

}
