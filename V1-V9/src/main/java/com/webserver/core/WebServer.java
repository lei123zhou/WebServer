package com.webserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private ServerSocket serverSocket;

    public WebServer() {

        try {
            System.out.println("准备一个服务器");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务器准备完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {

        try {
            System.out.println("等待一个客服端链接");
            Socket socket = serverSocket.accept();
            System.out.println("一个客服端链接成功");
            ClientHandler handler = new ClientHandler(socket);
            Thread thread = new Thread(handler);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        server.start();
    }
}
