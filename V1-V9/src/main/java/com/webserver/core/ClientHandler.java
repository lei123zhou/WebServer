package com.webserver.core;

import com.webserver.http.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            HttpRequest request = new HttpRequest(socket);
            File file = new File("./webApps/myWeb/index.html");
            OutputStream out = socket.getOutputStream();
            String line = "Http/1.1 200 OK";
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);
            line = "Content-Type: Text/html";
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);
            line = "Content-Length: " + file.length() + "";
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);
            out.write(13);
            out.write(10);
            FileInputStream fis = new FileInputStream(file);
            int d;
            byte[] data = new byte[10 * 1024];
            while ((d = fis.read(data)) != -1) {
                out.write(data, 0, d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
