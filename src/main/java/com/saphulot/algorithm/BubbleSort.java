package com.saphulot.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序：对数组相邻两个数字进行比较，交换。需要控制外层循环保证所有的数据比较到,拿到比较的第一个数字，内存循环拿到后续的数字进行比较交换
 */
public class BubbleSort {

    public void sort(Integer[] a){
        for (int i = 0; i<a.length; i++){
            for(int j = i+1; j<a.length;j++){
                if(compare(a[i],a[j])){
                    exchange(a,i,j);
                }
            }
        }
    }

    public boolean compare(Integer a1,Integer a2){
        return a1.compareTo(a2) >0;
    }

    public void exchange(Integer[] a,int i,int j){
        int temp = 0;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] a = {12,13,15,7,9,6,3};
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
