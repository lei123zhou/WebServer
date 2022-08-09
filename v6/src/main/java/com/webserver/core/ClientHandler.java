package com.webserver.core;

import com.webserver.http.HttpRequest;

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
            String path = request.getUri();
            System.out.println(path);
            /**
             * 将webapps/myweb/index.html页面响应给浏览器
             * 从存放所有网络应用资源的webApps下再根据请求的抽象路径定位资源*/
            File file = new File("./webApps" + path);
            /**
             * HTT[/1.1 200 OK(CRLF)
             * Content-Type:text/html(CRLF)
             * Content-Length:2546(CRLF)(CRLF)
             * 101101010*/
            OutputStream out = socket.getOutputStream();
            //3.1发送状态行
            String line = "HTTP/1.1 200 OK";
            out.write(line.getBytes("ISO8859-1"));//把字符串转化为二进制
            out.write(13);//发送了一个回车
            out.write(10);//发送了一个换行符
            //3.2发送响应头
            line = "Content-Type: text/html";
            out.write(line.getBytes("ISO8859-1"));//把字符串转化为二进制
            out.write(13);//发送了一个回车
            out.write(10);//发送了一个换行符
            line = "Content-Length: " + file.length();
            out.write(line.getBytes("ISO8859-1"));//把字符串转化为二进制
            out.write(13);//发送了一个回车
            out.write(10);//发送了一个换行符
            //单独发送CRLF表示响应头发送完毕
            out.write(13);//发送了一个回车
            out.write(10);//发送了一个换行符
            //3.3发送响应正文
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[1024 * 10];//10K
            int len;
            while ((len = fis.read(data)) != -1) {
                out.write(data, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
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
