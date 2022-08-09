package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//这个是用来回应浏览器的
public class HttpResponse {
    private int statusCode = 200;
    private String statusReason = "OK";
    private File entity;
    private Socket socket;
    public static final int CR = 13;
    public static final int LF = 10;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(Socket socket) {
        this.socket = socket;
    }

    public void flush() throws IOException {
        sendLine();
        sendHeader();
        sendContent();
    }

    private void sendLine() throws IOException {
        String line = "HTTP/1.1" + "" + statusCode + "" + statusCode;
        println(line);
    }

    private void sendHeader() throws IOException {
        Set<Map.Entry<String, String>> set = headers.entrySet();
        for (Map.Entry<String, String> e : set) {
            String name = e.getKey();
            String value = e.getValue();
            String line = name + ": " + value;
            println(line);
        }
    }

    private void sendContent() throws IOException {
        OutputStream out = socket.getOutputStream();
        try (FileInputStream fis = new FileInputStream(entity);) {
            byte[] data = new byte[1024 * 10];
            int len;
            while ((len = fis.read(data)) != -1) {
                out.write(data, 0, len);
            }
        }
    }


    private void println(String line) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(line.getBytes("ISO8859-1"));
        out.write(CR);
        out.write(LF);
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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
