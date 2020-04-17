package com.saphulot.lambda;

import java.util.function.Supplier;
/*
    Supplier用法：
        Supplier<SupplierDemo> sup = SupplierDemo::new;
        生成一个Supplier容器，调用get方法产生一个对应的对象。每次都是不同的对象。（对象生成器/创建对象的工厂）
 */
public class SupplierDemo {
    private String name = "xiaowang";
    public SupplierDemo() {
        System.out.println(name);
    }

    public static String getSupplier(Supplier<String> supplier) {
        return supplier.get();
    }

    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "world";

        String s3 = getSupplier(() -> s1 + s2);
        System.out.println(s3);
        // 生成supplier容器
        Supplier<SupplierDemo> sup = SupplierDemo::new;

        SupplierDemo supplierDemo = sup.get();
    }
}
