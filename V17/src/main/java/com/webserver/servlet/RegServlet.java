package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 用于处理用户注册业务的类
 */
public class RegServlet {
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("Reg开始处理注册");

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
        System.out.println("Reg处理注册完毕");
    }
}
