package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer主类
 * WebServer是一个Web容器，实现了Tomcat的几处功能
 */
public class WebServer {
    private ServerSocket serverSocket;
    private ExecutorService threadPool;//创建一个线程池

    public WebServer() {
        try {
            System.out.println("正在启动服务端");

            serverSocket = new ServerSocket(8088);
            threadPool = Executors.newFixedThreadPool(50);
            System.out.println("服务端启动完毕！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            while (true) {
                System.out.println("等待客户端连接");
                Socket socket = serverSocket.accept();
                System.out.println("一个客户端连接了");
//            启动一个线程来处理一个客服端的交互
                ClientHandler handler = new ClientHandler(socket);
              threadPool.execute(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        server.start();
    }
}
