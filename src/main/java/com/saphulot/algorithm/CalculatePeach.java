package com.saphulot.algorithm;



/**
 * 问题描述：有一堆桃子，猴子第一天吃了其中的一半，并再多吃了一个！以后每天猴子都吃其中的一半，然后再 多吃一个。
 * 当到第十天时，想再吃时（还没吃），发现只有 1 个桃子了。问题：最初共多少个桃子？
 *
 * 分析：递归算法  反向分析 设函数peach为第x天剩下的桃子数，则x-1天的桃子数为2peach(x+1)+2, 第10天为peach(10) = 1
 */
public class CalculatePeach {
    /**
     *
     * @param x 天数
     *
     * @return
     */
    public static int peach(int x){
        if (x == 10){
            return  1;
        }
        return 2*peach(x+1)+2;
    }

    public static void main(String[] args) {
        System.out.println(peach(1));
        int j = 0;
        for(int i = 0; i < 10; i++){
            j = (j++);
        }
        System.out.println(j);
    }
}
