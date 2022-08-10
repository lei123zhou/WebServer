package com.webserver.controller;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.Article;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理与文章相关业务
 */
public class ArticleController {
    public void create(HttpRequest request, HttpResponse response) {
        System.out.println("开始处理发表文章");
        String title = request.getParameters("title");
        String author = request.getParameters("author");
        String content = request.getParameters("content");
        try (
                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(
                                "./articles/" + title + ".obj"
                        )
                )
        ) {
            Article article = new Article(title, author, content);
            oos.writeObject(article);
            response.setEntity(
                    new File("./webApps/myWeb/article_success.html")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("文章处理完毕");
    }

    public void showAllArticle(HttpRequest request, HttpResponse response) {
        System.out.println("开始");
        //扫面users目录下所有的.obj文件并反序列化得到所有的User对象
        File articleDir = new File("./articles");
        File[] subs = articleDir.listFiles(e -> e.isFile() && e.getName().endsWith(".obj"));
        List<Article> articleList = new ArrayList<Article>();
        for (File userFile : subs) {
            try (
                    ObjectInputStream ois = new ObjectInputStream(
                            new FileInputStream(userFile)
                    )
            ) {
                Article article = (Article) ois.readObject();
                articleList.add(article);
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
            pw.println("<title>文章列表</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<center>");
            pw.println("<h1>文章列表</h1>");
            pw.println("<table border=\"1\">");
            pw.println("<tr>");
            pw.println("<td>标题</td>");
            pw.println("<td>作者</td>");
            pw.println("</tr>");
            for (Article article : articleList) {
                pw.println("<tr>");
                pw.println("<td ><a herf='./showArticle?title='>" + article.getTitle() + "</a></td>");
                pw.println("<td>" + article.getAuthor() + "</td>");
                pw.println("<td>" + article.getContent() + "</td>");
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
