package com.saphulot.lambda;

import java.util.function.Supplier;

/*
    Supplier用法：T类型数据提供者，本质接口，具体需求实现接口即可，lambda表达式书写。
    求数组最大值
 */
public class MaxValue {

    public static int getSupplier(Supplier<Integer> supplier) {
        return supplier.get();
    }

    public static void main(String[] args) {
        int[] arr = {100, 35, -434, 2324, 345};

        int value = getSupplier(() -> {
            int max = arr[0];
            for (int i : arr) {
                if (i > max) {
                    max = i;
                }
            }
            return max;
        });

        System.out.println(value);
    }
}
