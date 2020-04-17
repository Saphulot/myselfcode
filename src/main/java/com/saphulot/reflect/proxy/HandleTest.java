package com.saphulot.reflect.proxy;

import java.lang.reflect.Proxy;

public class HandleTest {
    public static void main(String[] args) {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Person stu = new Student("李四");

        StudentInvokeHandle handle = new StudentInvokeHandle(stu);

        Person proxy = (Person)Proxy.newProxyInstance(stu.getClass().getClassLoader(), stu.getClass().getInterfaces(), handle);

        proxy.giveMoney(600);
    }
}
