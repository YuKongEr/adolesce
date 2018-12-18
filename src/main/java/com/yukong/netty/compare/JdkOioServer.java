package com.yukong.netty.compare;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: yukong
 * @date: 2018/12/18 15:52
 */
public class JdkOioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        try{
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection form" + clientSocket);
                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.execute(() -> {
                    try {
                        OutputStream outputStream = clientSocket.getOutputStream();
                        outputStream.write("World\n".getBytes());
                        outputStream.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
