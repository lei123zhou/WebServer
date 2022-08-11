package com.webserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标注处理某个请求路径的方法
 * 该注解要求传入一个字符串参数,指定该注解标注的方法时哪个请求的
 * 例如:
 * @RequestMapping("/myWeb/regUser")*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
     String value();
}
