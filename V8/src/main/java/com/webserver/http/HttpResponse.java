package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 响应对象
 * 该类的每一个实例用于表示一个HTTP的响应,每个响应应当包含三部分:
 * 状态行,响应头,响应正文
 */
public class HttpResponse {
    //状态行相关信息
    private int statusCode = 200;//状态代码(默认值为200)
    private String statusReason = "OK";//状态描述
    //响应头相关信息
    private File entity;//正文对应的一个实体文件
    private Socket socket;

    public HttpResponse(Socket socket) {
        this.socket = socket;
    }

    /**
     * 将当前响应对象内容按照标准的响应格式发送给客户端
     */
    public void flush() throws IOException {
        //1.发送状态行
        sendStatusLine();
        //2.发送响应头
        sendHeaders();
        //3.发送响应正文
        sendContent();

    }

    //响应相关信息
    private void sendStatusLine() throws IOException {
        String line = "HTTP/1.1" + "statusCode" + "statusReason";
        println(line);
    }

    private void sendHeaders() throws IOException {
        String line = "Content-Type: text/html";
        println(line);
        line = "Content-Length: " + entity.length();
        println(line);
        //单独发送CRLF表示响应头发送完毕
        println("");
    }

    private void sendContent() throws IOException {
        OutputStream out = socket.getOutputStream();
        /**
         * 不用写catch,因为异常要抛给流程控制,所以在方法上定义了throws
         * 不写finally是因为上面的try使用了自动关闭的特性,编译器会补充finally*/
        try (FileInputStream fis = new FileInputStream(entity);) {
            byte[] data = new byte[1024 * 10];//10K
            int len;
            while ((len = fis.read(data)) != -1) {
                out.write(data, 0, len);
            }
        }

    }

    private void println(String line) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(line.getBytes("ISO8859-1"));//把字符串转化为二进制
        out.write(13);//发送了一个回车
        out.write(10);//发送了一个换行符
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;
    }
}
