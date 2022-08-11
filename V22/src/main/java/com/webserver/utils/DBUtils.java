package com.webserver.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    private static DruidDataSource dds;
    static {
        //静态块里面的代码只执行一次
        //创建数据库连接池
        dds = new DruidDataSource();
        //设置数据库链接信息
        dds.setUrl("jdbc:mysql://localhost:3306/wsdb?" +
                "characterEncoding=utf8&" +
                "serverTimezone=Asia/Shanghai");
        dds.setUsername("root");
        dds.setPassword("520927zl");
        dds.setInitialSize(3);//设置初始链接数量;
        dds.setMaxActive(5);//设置最大连接数量
    }

    //获取封装链接方法
    public static Connection getCon() throws SQLException {

        //从链接池中获取链接 异常抛出
        Connection conn = dds.getConnection();
        System.out.println(conn);
        return conn;
    }

}
