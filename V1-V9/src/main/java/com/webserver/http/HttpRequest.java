package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    //请求行相关信息
    private String method;//请求方式
    private String protocol;//协议版本
    private String uri;//抽象路径
    private Socket socket;
    private Map<String, String> headers = new HashMap<>();

    public HttpRequest(Socket socket) throws IOException {
        this.socket = socket;
        //1解析请求行
        parseRequestLine();
        //2解析消息头
        parseHeaders();
        //3解析消息正文
        parseContent();
    }

    private void parseRequestLine() throws IOException {
        String line = readLine();
        String[] data = line.split("\\s");
        method = data[0];
        protocol = data[1];
        uri = data[2];
        System.out.println(method + protocol + uri);

    }

    private void parseHeaders() throws IOException {
        String line;
        while (true) {
            line = readLine();
            if (line.isEmpty()) {
                break;
            }
            System.out.println(line);
            String[] data = line.split(":\\s");
            headers.put(data[0], data[1]);
        }
    }

    private void parseContent() {
    }

    public String readLine() throws IOException {
        //读取文件
        InputStream is = socket.getInputStream();
        StringBuilder builder = new StringBuilder();
        int d;
        char cur = 'a', pre = 'a';
        while ((d = is.read()) != -1) {
            cur = (char) d;
            if (cur == 10 && pre == 13) {
                //说明读到CRLF了
                break;
            }
            builder.append(cur);
            pre = cur;
        }
        return builder.toString().trim();
    }

}
