package com.yukong.sort;

/**
 * @author yukong
 * @date 2019-05-13 13:58
 * 215. Kth Largest Element in an Array
 *
 *
 *
 *
 * 题目描述
 * 评论 (253)
 * 题解New
 * 提交记录
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * Example 1:
 *
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5
 * Example 2:
 *
 * Input: [3,2,3,1,2,4,5,5,6] and k = 4
 * Output: 4
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
public class QuickSort {


    private int division(int[] list, int left, int right) {
        int base = list[left];
        while(left < right) {
            // 从右往左找到第一个比base小的数
            while(left < right  && base < list[right]){
                right --;
            }
            // 交换
            list[left] = list[right];
            // 从左往右找到第一个比base大的数
            while (left  < right && base > list[left]){
                left ++;
            }
            list[right] = list[left];
        }
        list[left] = base;
        return left;
    }

    private void quickSort(int[] list, int left, int right) {
        if (left < right) {
            int base = division(list, left, right);
            quickSort(list,left, base - 1);
            quickSort(list, base + 1, right);
        }
    }

    private int findKthLargest(int[] list, int k){
         k = list.length - k;
        int l = 0, h = list.length - 1;
        while (l < h) {
            int j = division(list, l, h);
            if (j == k) {
                break;
            } else if (j < k) {
                l = j + 1;
            } else {
                h = j - 1; }
        }
        return list[k];
    }

    public static void main(String[] args) {
        int[] list = {4,3,5,6,2,1};
        new QuickSort().quickSort(list, 0, list.length -1);
        for (int i : list) {
            System.out.print(i + " ");
        }

        System.out.println(new QuickSort().findKthLargest(list, 4));
    }


}
