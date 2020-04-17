package com.saphulot.reflect.proxy;

public class NormalStudentProxy implements Person {
    private Student student;

    public NormalStudentProxy(Student student) {
        this.student = student;
    }

    @Override
    public void giveMoney(int money) {
        System.out.println(student.getName()+"最近学习成绩好！");
        student.giveMoney(money);
    }
}
