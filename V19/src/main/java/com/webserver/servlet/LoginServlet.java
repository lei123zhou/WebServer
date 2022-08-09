package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoginServlet {
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("开始处理登陆");
        String username = request.getParameters("userName");
        String password = request.getParameters("password");
        if (username == null || password == null) {
            response.setEntity(new File("./webApps/myWeb/login_error.html"));
            return;
        }
        File userFile = new File("users/" + username + ".obj");
        if (userFile.exists()) {
            try (
                    ObjectInputStream ois = new ObjectInputStream(
                            new FileInputStream(userFile)
                    )
            ) {
                User user = (User) ois.readObject();
                if (user.getPassword().equals(password)) {
                    response.setEntity(new File("./webApps/myWeb/login_success.html"));
                    return;
                } else {
                    response.setEntity(new File("./webApps/myWeb/login_error.html"));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.setEntity(new File("./webApps/myWeb/login_error.html"));
            return;
        }
        System.out.println("处理登陆完毕");
    }
}
