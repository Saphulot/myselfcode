package com.saphulot.lambda;

public class Lambda {
    public static void main(String[] args) {

        dinner(() -> {
            System.out.println("chicken");
        });
    }

    public static void dinner(Cook cook){
        cook.invoke();
    }
}
