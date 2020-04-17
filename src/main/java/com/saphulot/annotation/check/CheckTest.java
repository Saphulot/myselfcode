package com.saphulot.annotation.check;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
    对于需要测试的类中各个方法，可以通过添加注解，获取到所有的方法循环测试，不用手动针对具体的方法测试
 */
public class CheckTest {
    public static void main(String[] args) throws IOException {
        // 获取到Calculate的class对象
        Calculate calculate = new Calculate();

        Class<? extends Calculate> aClass = calculate.getClass();


        // 获取所有的方法
        Method[] methods = aClass.getMethods();

        //定义一个错误输出日志文件
        BufferedWriter bw = new BufferedWriter(new FileWriter("errlog.txt"));
        int num = 0;
        for (Method method : methods) {
            if(method.isAnnotationPresent(check.class)){
                try {
                    method.invoke(calculate);
                } catch (Exception e) {
                    num ++;
                    bw.write(method.getName() + " 方法异常。。。");
                    bw.newLine();
                    bw.write("异常名称： "+e.getCause().getClass().getSimpleName());
                    bw.newLine();
                    bw.write("异常原因： "+e.getMessage());
                    bw.newLine();
                    bw.write("---------------------------");
                    bw.newLine();
                }
            }
        }
        bw.write("一共有"+num+"次异常");
        bw.flush();
        bw.close();
    }
}
