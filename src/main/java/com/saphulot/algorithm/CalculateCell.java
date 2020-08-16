package com.saphulot.algorithm;

public class CalculateCell {
    /**
     * 问题描述：有一个细胞，每一个小时分裂出一个细胞，细胞存活3小时，n个小时后有多少存活的细胞
     * 分析：123个小时代表细胞三种状态abc，递归找出某一个具体时间abc三种状态细胞的关系
     */

    public static int allCell(int n){
        return aCell(n) + bCell(n) + cCell(n);
    }

    /**
     * a状态细胞等于上一个小时所有状态细胞数
     * @param n
     * @return
     */
    public static int aCell(int n){
        if (n == 1){
            return 1;
        }else {
            return aCell(n-1) + bCell(n-1) +cCell(n-1);
        }
    }

    /**
     * b状态的细胞等于上一个小时a状态细胞数量
     * @param n
     * @return
     */
    public static int bCell(int n){
        if (n == 1){
            return 0;
        }else {
            return aCell(n-1);
        }
    }

    /**
     * c状态细胞等于上一个小时b状态细胞
     * @param n
     * @return
     */
    public static int cCell(int n){
        if (n == 1){
            return 0;
        }else {
            return bCell(n-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(allCell(2));
    }
}
