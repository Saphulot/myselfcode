package com.saphulot.annotation.check;

public class Calculate {
    public Calculate() {
    }

    @check
    public void add(){
        System.out.println("1 + 0 = "+(1 + 0));
    }

    @check
    public void rev(){
        System.out.println("1 - 0 = "+(1 - 0));
    }

    @check
    public void dev(){
        System.out.println("1 / 0 = " +(1/0));
    }
}
