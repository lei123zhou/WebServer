package com.webserver.servlet;
/**
 * 发表文章
 */

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.Article;

import java.io.*;

public class CreateArticleServlet implements Serializable {
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("开始处理发表文章");
        String title = request.getParameters("title");
        String author = request.getParameters("author");
        String content = request.getParameters("content");
        try(
                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(
                                "./articles/"+title+".obj"
                        )
                )
        ){
            Article article = new Article(title,author,content);
            oos.writeObject(article);
            response.setEntity(
                    new File("./webApps/myWeb/article_success.html")
            );
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("文章处理完毕");
    }
}
