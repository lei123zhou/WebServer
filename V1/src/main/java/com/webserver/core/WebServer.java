package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * WebServer主类
 * WebServer是一个Web容器，实现了Tomcat的几处功能
 */
public class WebServer {
    private ServerSocket serverSocket;

    public WebServer() {
        try {
            System.out.println("正在启动服务端");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务端启动完毕！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            System.out.println("等待客户端连接");
            Socket socket = serverSocket.accept();
            System.out.println("一个客户端连接了");
            InputStream in = socket.getInputStream();
            int d;
            while ((d = in.read()) != -1) {
                System.out.print((char) d);
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
