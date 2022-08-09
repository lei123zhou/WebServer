package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 *用来生成显示所有注册用户的动态页面
 * */
public class ShowAllUserServlet {
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("开始");
        //扫面users目录下所有的.obj文件并反序列化得到所有的User对象
        File usersdIR = new File("/users");
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
