package com.saphulot.reflect.proxy;

public class Student implements Person {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void giveMoney(int money) {
        System.out.println(name + "交了 " + money + "元 学费");
    }
}
