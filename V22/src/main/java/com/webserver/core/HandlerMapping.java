package com.webserver.core;

import com.webserver.annotation.Controller;
import com.webserver.annotation.RequestMapping;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存所有请求与处理类的对应关系
 */
public class HandlerMapping {
    /**
     * 保存所有请求与对应处理方法
     * key:请求路径
     * value:处理该请求的某个Controller中的对应方法
     * 例如:
     * key:'/myweb/regUser'
     * value:UserController中的reg方法
     */
    private static Map<String, MethodMapping> mapping = new HashMap();

    static {
        initMapping();
    }

    private static void initMapping() {
        try {
//扫描com.webserver.controller包中的所有类
            //1.定位目录
            File dir = new File(
                    HandlerMapping.class.getClassLoader()
                            .getResource("com/webserver/controller").toURI()
            );
            //1.2获取该目录中所有的.class文件用于确定有多少个类
            File[] subs = dir.listFiles(e -> e.getName().endsWith(".class"));
            //1.3根据字节码文件的文件名来加载类对象
            for (File sub : subs) {
                String fileName = sub.getName();
                String className = fileName.substring(0, fileName.indexOf("."));
                //加载类对象
                Class cls = Class.forName("com.webserver.controller." + className);
                //2筛选所有被@Controller修饰的类
                if (cls.isAnnotationPresent(Controller.class)) {
                    //实例化这个Controller备用
                    Object controller = cls.newInstance();
                    //3筛选该Controller中被@RequestMapping修饰的方法
                    Method[] methods = cls.getDeclaredMethods();
                    for (Method m : methods) {
                        if (m.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping rm = m.getAnnotation(RequestMapping.class);
                            String path = rm.value();
                            //讲业务方法和该方法所属对象构建成一个MethodMapping对像
                            MethodMapping methodMapping = new MethodMapping();
                            methodMapping.setMethod(m);
                            methodMapping.setObj(controller);
                            //将请求路径与处理该请求的业务类实例(某Controller实例)和对应方法
                            //存入mapping中
                            mapping.put(path, methodMapping);
                        }
                    }
                }
            }
//            mapping.forEach((k, v) -> System.out.println("请求路径" + k + "处理类" + v.getObj() + "处理方法" + v.getMethod().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理业务的方法与其所属类的对应关系
     * 在反射中我们调用某个方法时的操作通常为:
     * method.inovke(obj)
     * method为方法对象，obj为该方法所属对象
     * <p>
     * 因为我们在反射中操作方式时要有两个必要条件,方法对象和该方法的
     * 所属对象
     */
    public static class MethodMapping {
        private Object obj;//方法的所属对象
        private Method method;//方法对象

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }

    /**
     * 根据给定的请求路径获取处理该路径的Controller以及对应的方法
     *
     * @param path
     * @return
     */
    public static MethodMapping getMethod(String path) {
        return mapping.get(path);
    }
}
