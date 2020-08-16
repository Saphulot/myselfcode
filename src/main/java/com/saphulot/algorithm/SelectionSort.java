package com.saphulot.algorithm;


import java.util.Arrays;

/**
 * 选择排序：假定第一个位置为最小索引位置，对后续待排数据进行比较记录更小数字索引位置，继续对后续数据进行比较，获取最小数字的索引位置和开始的数字交换
 */
public class SelectionSort {
    public void sort(Integer[] a){

        for(int i = 0; i < a.length-1; i++){
            int minIndex = i;
            for(int j = i+1; j < a.length; j++){
                if(compare(a[minIndex],a[j])){
                    minIndex = j;
                }
            }
            exchange(a,i,minIndex);
        }
    }

    public boolean compare(Integer a,Integer b){
        return a.compareTo(b)>0;
    }

    public void exchange(Integer[] a, int i, int j){
        int temp = 0;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] a = {1,23,3,5,12,4,9,6};
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
