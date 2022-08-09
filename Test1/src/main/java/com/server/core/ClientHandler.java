package com.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理与指定客户端的HTTP交互
 * HTTP协议要求客户端与服务端交互为"一问一答"原则.因此当客户端连接
 * 服务端后,服务端便会启动一个线程来执行ClientHandler.
 * 这里分为三步完成一次HTTP交互处理:
 * 1:解析请求(读取客户端发送过来的HTTP请求内容)
 * 2:处理请求(分析请求内容理解意图并进行对应的处理)
 * 3:发送响应(将处理结果包含在一个HTTP响应内容中,将其回复给客户端)
 */
public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            //1解析请求

            //1.1解析请求行
            String line = readLine();
            System.out.println("请求行:" + line);
            String method;//请求方式
            String uri;//抽象路径
            String protocol;//协议版本

            String[] data = line.split("\\s");
            method = data[0];
            uri = data[1];
            protocol = data[2];

            System.out.println("method:" + method);
            System.out.println("uri:" + uri);
            System.out.println("protocol:" + protocol);

            //1.2解析消息头
            //存放所有消息头的Map,key:消息头的名字 value:消息头的值
            Map<String, String> headers = new HashMap<>();
            while (true) {
                line = readLine();
                //读取请求行时如果返回的是空字符串,说明单独读取到了CRLF
                if (line.isEmpty()) {
                    break;
                }
                System.out.println("消息头:" + line);
                data = line.split(":\\s");
                headers.put(data[0], data[1]);
            }
            System.out.println("headers:" + headers);


            //2处理请求

            //3发送响应

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //交互完毕后与客户端断开连接(HTTP协议要求)
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String readLine() throws IOException {
        /*
            调用同一个socket的getInputStream方法时,无论调用多少次
            返回的输入流始终是同一个流.输出流也是一样的.
         */
        InputStream in = socket.getInputStream();
        StringBuilder builder = new StringBuilder();
        //cur表示本次读取到的字符,pre表示上次读取到的字符
        char cur = 'a', pre = 'a';
        int d;
        while ((d = in.read()) != -1) {
            cur = (char) d;
            //上次读到的是回车符本次读取到的是换行符
            if (pre == 13 && cur == 10) {
                break;
            }
            builder.append(cur);
            pre = cur;
        }
        return builder.toString().trim();
    }

}
