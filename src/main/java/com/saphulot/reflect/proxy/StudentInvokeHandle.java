package com.saphulot.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StudentInvokeHandle implements InvocationHandler {
    private Object object;

    public StudentInvokeHandle(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行动态代理之前。。。。");
        System.out.println(proxy.getClass());
        method.invoke(object,args);
        System.out.println("执行动态代理之后。。。。");
        return null;
    }
}
