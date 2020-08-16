package com.saphulot.algorithm;

import java.util.Arrays;

/**
 * 希尔排序：按增量h对数组分组，分别对分组后的子组进行插入排序。
 */
public class ShellSort {
    public void sort(Comparable[] a){
        int hi = a.length;
        int h = 1;
        //确定初始步长
        while (h < hi/2){
            h = h*2+1;
        }

        while(h>=1){
            //找出该步长所有待插入数据，即为h后面所有元素
            for(int i=h; i< hi; i++){
                //对所有待插入数据进行h步长分组做插入排序（反向思考）
                for(int j=i; j>=h;j-=h){
                    if(compare(a[j-h],a[j])){
                        exchange(a,j-h,j);
                    }
                    else {
                        break;
                    }
                }
            }
            // 控制步长躺数
            h = h/2;
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
        ShellSort shellSort = new ShellSort();
        shellSort.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
