package com.saphulot.reflect.proxy;

public class Test {
    public static void main(String[] args) {
        Student stu = new Student("张三");

        Person pro_stu = new NormalStudentProxy(stu);
        pro_stu.giveMoney(500);
    }

}
