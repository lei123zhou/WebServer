package com.webserver.vo;

import java.io.Serializable;

/**
 * VO:value object
 * 这种对象仅用来保存一组数据使用
 * <p>
 * 该类的每一个实例用于表示一个用户信息
 */
public class User implements Serializable {
    private String username;
    private String password;
    private int age;
    private String nickName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public User(String username, String password, int age, String nickName) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
