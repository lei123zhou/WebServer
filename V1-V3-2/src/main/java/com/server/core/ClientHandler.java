package com.server.core;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String line = readLine();
            String[] data = line.split("\\s");
            System.out.println("请求方式:" + data[0] + "uri:" + data[1] + "protocol:" + data[2]);
            HashMap hasMap = new HashMap();
            while (true) {
                line = readLine();
                if (line.isEmpty()) {
                    break;
                }
                System.out.println(line);
                data = line.split(":\\s");
                hasMap.put(data[0], data[1]);
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

    public String readLine() throws IOException {
        InputStream is = socket.getInputStream();
        StringBuilder sb = new StringBuilder();
        int d;
        char cur = 'a', pre = 'a';
        while ((d = is.read()) != -1) {
            cur = (char) d;
            if (cur == '\n' && pre == '\r') {
                break;
            }
            sb.append(cur);
            pre = cur;
        }
        return sb.toString().trim();
    }

    ;
}
