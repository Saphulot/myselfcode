package com.saphulot.algorithm;

import java.util.Arrays;

public class QuikSort {

    public void sort(Comparable[] a){
        int lo = 0;
        int hi = a.length-1;
        sort(a,lo,hi);
    }

    public void sort(Comparable[] a, int lo, int hi){
        if(lo >= hi){
            return;
        }

        int partition = partition(a, lo, hi);

        sort(a,lo,partition-1);

        sort(a,partition+1,hi);
    }

    public int partition(Comparable[] a, int lo,int hi){
        int left = lo;
        int right = hi+1;

        while (true){
            // 右指针先左移，当右子组指针移动到某个位置right时，此位置的值比lo的值小的时候退出循环，right记录此位置
            while (compare(a[lo],a[--right])){
                if(right == lo){
                    break;
                }
            }
            // 左指针后右移，当左子组指针移动到某个位置left时，此位置的值比lo的值大的时候退出循环，left记录此位置
            while (compare(a[++left],a[lo])){
                if(left == hi){
                    break;
                }
            }

            // 当满足条件的左右指针的值存在并且不越界时 交换指针位置的值
            if(left<right){
                exchange(a,left,right);
            }else {
                break;
            }

        }
        //右指针先移动 取right
        exchange(a,lo,right);
        return right;
    }

    public boolean compare(Comparable a, Comparable b){
        return a.compareTo(b)<0;
    }

    public void exchange(Comparable[] a, int i, int j){
        Comparable temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] a = {1,23,3,5,12,4,9,6};
        QuikSort quikSort = new QuikSort();
        quikSort.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
