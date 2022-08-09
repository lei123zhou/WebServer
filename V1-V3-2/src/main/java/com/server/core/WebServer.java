package com.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private ServerSocket serverSocket;

    public WebServer() {
        try {
            System.out.println("准备服务器中");
            this.serverSocket = new ServerSocket(8088);
            System.out.println("服务器已经启动");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            System.out.println("等待一个客户端连接");
            Socket socket = this.serverSocket.accept();
            System.out.println("一个客户端连接成功");
            ClientHandler handler = new ClientHandler(socket);
            Thread t = new Thread(handler);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServer ws = new WebServer();
        ws.start();
    }
}
