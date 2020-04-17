package com.saphulot.lambda;

import java.util.function.Consumer;

/*
    Consumer接口使用方式：数据的消费者，如何消费具体实现accept方法。
    andThen 消费连接
 */
public class ConsumerDemo {
    public static void doConsumer(String str, Consumer<String> consumer) {
        consumer.accept(str);
    }

    public static void consumerAnd(String str, Consumer<String> con1, Consumer<String> con2) {
        con1.andThen(con2).accept(str);
    }

    public static void main(String[] args) {
        String str = "Hello";

        doConsumer(str, (t) -> {
            System.out.println(str.toUpperCase());
        });
        // andThen
        consumerAnd(str,
                (t) -> {
                    System.out.println(t.toLowerCase());
                },
                (t) -> {
                    System.out.println(t.toUpperCase());
                });


    }
}
