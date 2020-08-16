package com.saphulot.algorithm;

import org.checkerframework.checker.units.qual.C;

/**
 * 归并排序O(nlogn)：递归思想 对数组拆分为两个子组，归并
 */
public class MergeSort {
    public Comparable[] assist;

    public void sort(Comparable[] a) {
        assist = new Comparable[a.length];
        int lo = 0;
        int hi = a.length - 1;
        sort(a, lo, hi);
    }

    public void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;

        //先拆分成两组
        sort(a, lo, mid);

        sort(a, mid + 1, hi);
        //
        merge(a, lo, mid, hi);
    }

    public void merge(Comparable[] a, int lo, int mid, int hi) {
        int p1 = lo;
        int p2 = mid + 1;
        int p = p1;

        //p1,p2一起向后移动，均没有移动到底时，比较值并赋值给assist对应位置并移动对应指针。
        while (p1 <= mid && p2 <= hi) {
            if (compare(a[p1], a[p2])) {
                //先赋值，然后将p和p2指针向后移动一位
                assist[p++] = a[p2++];
            } else {
                assist[p++] = a[p1++];
            }
        }

        //当p2比较完，p1还有剩余时，将p1后续值赋值给assist数组
        while (p1 <= mid) {
            assist[p++] = a[p2++];
        }

        //当p1比较完，p2还有剩余时，将p2后续值赋值给assist数组
        while (p2 <= hi) {
            assist[p++] = a[p2++];
        }

        for (int i = lo; i <= hi; i++) {
            a[i] = assist[i];
        }

    }

    public boolean compare(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }


}
