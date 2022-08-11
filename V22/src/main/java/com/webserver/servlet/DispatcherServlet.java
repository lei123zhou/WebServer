package com.webserver.servlet;

import com.webserver.controller.ArticleController;
import com.webserver.controller.UserController;
import com.webserver.core.HandlerMapping;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理请求环节
 */
public class DispatcherServlet {
    public void service(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        String path = request.getRequestURI();
        //首先通过请求判定是否为请求一个业务
        HandlerMapping.MethodMapping methodMapping =
                HandlerMapping.getMethod(path);
        if (methodMapping != null) {
            Method method = methodMapping.getMethod();
            Object obj = methodMapping.getObj();
            method.invoke(obj, request, response);
        } else {
            File file = new File("./webApps" + path);

            if (file.exists() && file.isFile()) {
                //文件存在则直接响应
                response.setEntity(file);

            } else {
                //文件不存在或者定位的是一个目录,则响应404
                response.setStatusCode(404);
                response.setStatusReason("Not Found");
                file = new File("./webApps/myWeb/404.html");
                response.setEntity(new File("./webApps/myWeb/404.html"));
                response.setEntity(file);
            }
        }

        response.putHeader("Server", "WebServer");
    }
}
