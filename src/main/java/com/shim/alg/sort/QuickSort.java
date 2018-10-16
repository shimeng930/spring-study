package com.shim.alg.sort;

import java.util.Arrays;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/10/16 10:15
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class QuickSort {

    private void swap(int[] arrays, int i, int j) {
        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }

    private int sort(int[] arrays, int left, int right) {
        int key = arrays[left];

        while (left < right) {
            while (left < right && arrays[right] >= key) {
                right--;
            }
            swap(arrays, left, right);
            while (left < right && arrays[left] <= key) {
                left++;
            }
            swap(arrays, left, right);
        }
        return left;
    }

    public void quick(int[] arrays, int left, int right) {
        if(left >= right){
            return;
        }
        int index = sort(arrays,left,right);//枢轴的位置
        quick(arrays,left,index - 1);
        quick(arrays,index + 1,right);
    }

    public static void main(String[] s){
        int[] arrays = {6, 8, 2, 5, 3, 1, 0, 7, 9, 4, 12};
        QuickSort demo = new QuickSort();
        demo.quick(arrays, 0, (arrays.length-1));
        System.out.println(Arrays.toString(arrays));
        System.out.println(Integer.MAX_VALUE);
    }
}
