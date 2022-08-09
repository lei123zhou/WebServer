package com.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * WebServer主类
 * WebServer是一个Web容器，实现了Tomcat的基础功能。
 */
public class WebServer {
    private ServerSocket serverSocket;

    public WebServer() {
        try {
            System.out.println("正在启动服务端...");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务端启动完毕!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            System.out.println("等待客户端链接...");
            Socket socket = serverSocket.accept();
            System.out.println("一个客户端链接了!");

            //启动一个线程来处理该客户端的交互
            ClientHandler handler = new ClientHandler(socket);
            Thread t = new Thread(handler);
            t.start();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        server.start();
    }
}

