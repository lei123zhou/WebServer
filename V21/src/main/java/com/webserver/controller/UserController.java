package com.webserver.controller;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理与用户相关的操作
 */
public class UserController {
    public void reg(HttpRequest request, HttpResponse response) {
        System.out.println("开始处理注册");

//1通过request获取注册页面上表单提交上来的注册信息
        //2将通过用户信息保存到硬盘上(序列化一个文件)
        //3设置response响应注册结果

        //1
        String userName = request.getParameters("userName");
        String passWord = request.getParameters("password");
        String ageStr = request.getParameters("age");
        String nickName = request.getParameters("nickName");
        System.out.println(userName + "," + passWord + "," + ageStr + "," + nickName);
        if (ageStr == null || !ageStr.matches("[0-9]+") || userName == null || passWord == null || nickName == null) {
            File file = new File("./webApps/myWeb/reg_error.html");
            response.setEntity(file);
            return;
        }
        int age = Integer.parseInt(ageStr);


        /**
         * 2 将该用户以User对象形式序列化到文件中
         * 文件名格式:用户名.obj
         * */
        /**
         * 判断用户是否注册如果是重复的用户则直接响应重复用户的提示页面:
         * have_user.html中显示一行字:该用户已存在,请重新注册
         * */
        File userFile = new File("./users/" + userName + ".obj");
        if (userFile.exists()) {
            response.setEntity(new File("./webApps/myWeb/have_user.html"));
            return;
        }
        try (
                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(
                                "./users/" + userName + ".obj"
                        )
                )
        ) {
            User user = new User(userName, passWord, age, nickName);
            oos.writeObject(user);
            File file = new File("./webApps/myWeb/reg_success.html");
            response.setEntity(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("处理注册完毕");
    }

    public void login(HttpRequest request, HttpResponse response) {
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

    public void showAllUser(HttpRequest request, HttpResponse response) {
        System.out.println("开始");
        //扫面users目录下所有的.obj文件并反序列化得到所有的User对象
        File usersdIR = new File("./users");
        File[] subs = usersdIR.listFiles(e -> e.isFile() && e.getName().endsWith(".obj"));
        List<User> userList = new ArrayList<>();
        for (File userFile : subs) {
            try (
                    ObjectInputStream ois = new ObjectInputStream(
                            new FileInputStream(userFile)
                    )
            ) {
                User user = (User) ois.readObject();
                userList.add(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter pw = response.getWriter();
            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"en\">");
            pw.println("<head>");
            pw.println("<meta charset=\"UTF-8\">");
            pw.println("<title>用户列表</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<center>");
            pw.println("<h1>用户列表</h1>");
            pw.println("<table border=\"1\">");
            pw.println("<tr>");
            pw.println("<td>用户名</td>");
            pw.println("<td>密码</td>");
            pw.println("<td>昵称</td>");
            pw.println("<td>年龄</td>");
            pw.println("</tr>");
            for (User user : userList) {
                pw.println("<tr>");
                pw.println("<td>" + user.getUsername() + "</td>");
                pw.println("<td>" + user.getPassword() + "</td>");
                pw.println("<td>" + user.getNickName() + "</td>");
                pw.println("<td>" + user.getAge() + "</td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("</center>");
            pw.println("</body>");
            pw.println("</html>");
            response.setContentType("text/html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
