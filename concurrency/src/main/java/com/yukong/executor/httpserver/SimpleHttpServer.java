package com.yukong.executor.httpserver;

import com.yukong.executor.DefaultThreadPool;
import com.yukong.executor.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yukong
 * @date 2018/9/5
 * @description 简单的http服务器
 */
public class SimpleHttpServer {

    /**
     * 处理HttpRequest的线程池
     */
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(1);

    /**
     * 服务器的根路径
     */
    static String basePath;

    static ServerSocket serverSocket;

    /**
     * 端口
     */
    static int port;

    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void start() throws IOException {
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            // 放入线程池等待执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }

    static class HttpRequestHandler implements Runnable {

        Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line = null;
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                System.out.println(header);
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                // 如果请求资源的后缀为jpg或者ico，则读取资源
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        byteArrayOutputStream.write(i);
                    }
                    byte[] array = byteArrayOutputStream.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");
                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(filePath)
                    ));
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: text/html");
                    out.println("");
                    while ((line = br.readLine()) != null) {
                        out.println(line);
                    }
                }
                out.flush();
            } catch (IOException e) {
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {
                close(br, in, reader, out, socket);
            }
        }

        private static void close(Closeable... closeables)  {
            if (closeables != null) {
                for (Closeable closeable:closeables) {
                    if (closeable != null) {
                        try {
                            closeable.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SimpleHttpServer.setPort(8999);
        SimpleHttpServer.setBasePath("D:\\学习\\adolesce\\concurrency\\src\\main\\java\\com\\yukong\\executor\\httpserver");
        SimpleHttpServer.start();
    }
}
