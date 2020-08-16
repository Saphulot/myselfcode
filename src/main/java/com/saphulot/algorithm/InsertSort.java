package com.saphulot.algorithm;

import java.util.Arrays;

/**
 * 插入排序：将数组分成两段，已排序的一段，待排序的一段，对待排序的第一个值和已排序的倒序循环比较插入
 */
public class InsertSort {
    public void sort(Comparable[] a){

        for(int i = 0; i < a.length; i++){
            for (int j = i; j > 0; j--){
                if (compare(a[j-1],a[j])){
                    exchange(a,j-1,j);
                }else {
                    break;
                }
            }
        }
    }

    public boolean compare(Comparable a,Comparable b){
        return a.compareTo(b)>0;
    }

    public void exchange(Comparable[] a, int i, int j){
        Comparable temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] a = {1,23,3,5,12,4,9,6};
        InsertSort selectionSort = new InsertSort();
        selectionSort.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
