package com.webserver.core;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.servlet.DispatcherServlet;
import src.main.java.com.webserver.http.EmptyRequestException;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理与指定客户端的HTTP交互
 * HTTP协议要求客户端与服务端交互为“一问一答”原则。因为当客户端连接
 * 服务端后,服务端便会启动一个线程来执行ClientHander
 * 这里分为三部完成一次HTTP交互处理
 * 1:解析请求(读取客户端发送过来的HTTP请求内容)
 * 2:处理请求(分析请求内容理解意图并进行对应的处理)
 * 3：发送响应(将处理结果包含在一个HTTP响应中,将其回复给客户端)
 */
public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            HttpRequest request = new HttpRequest(socket);
            HttpResponse response = new HttpResponse(socket);
            DispatcherServlet servlet = new DispatcherServlet();
            servlet.service(request, response);

            /**
             * 将webapps/myweb/index.html页面响应给浏览器
             * 从存放所有网络应用资源的webApps下再根据请求的抽象路径定位资源*/
            //3.1发送状态行
            //3.2发送响应头
            //3.3发送响应正文
            response.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyRequestException e) {
            //空请求异常捕获后什么都不同坐
        } finally {
            try {
                //交互完毕后与客户端断开链接(HTTP协议要求的)
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
