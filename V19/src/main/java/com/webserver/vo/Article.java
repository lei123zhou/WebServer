package com.webserver.vo;

import java.io.Serializable;

/**
 *该类的每一个实例用于表示一篇文章
 *
*/
public class Article implements Serializable {
    private String title;
    private String author;
    private String content;

    public Article(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
