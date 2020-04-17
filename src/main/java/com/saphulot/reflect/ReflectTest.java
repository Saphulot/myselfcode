package com.saphulot.reflect;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;

/*
    在不修改源代码的情况下用配置文件形式调用不同对象的方法，反射的作用
 */
public class ReflectTest {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();

        // 定位资源的两种方式
        //class.getResource("/") == class.getClassLoader().getResource("")
        //URL resource1 = ReflectTest.class.getResource("/class.properties");
        //InputStream resourceAsStream1 = ReflectTest.class.getResourceAsStream("/class.properties");
        //System.out.println(resource1);


        // 定位到字节码文件路径
        ClassLoader classLoader = ReflectTest.class.getClassLoader();
        // 获取类加载的资源文件
        InputStream resourceAsStream = classLoader.getResourceAsStream("class.properties");
        // 加载配置文件
        properties.load(resourceAsStream);
        // 获取对应的类名和方法名
        String className = properties.getProperty("className");

        String methodName = properties.getProperty("methodName");
        // 加载配置文件定义的类
        Class cls= Class.forName(className);
        // 生存对象
        Object obj = cls.newInstance();

        Method method = cls.getMethod(methodName,String.class);

        method.invoke(obj,"rice");

    }
}
