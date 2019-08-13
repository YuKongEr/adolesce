package com.yukong.graphalgorithm._1_binary_search;

/**
 * @author yukong
 * @date 2019-05-14 16:11
 */
public class BinarySearch {

    /**
     * 二分钟查找
     * 双指针模式
     * @param items 原数组 (有序)
     * @param target 要查找的数字
     * @return 返回目标数字在目标数组中的下标 如果不存在 则返回 -1
     */
    public int search(int[] items, int target) {
        int low = 0;
        int high = items.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            // 如果相等 代表查找到  返回下标
            if(target == items[mid]) {
                return mid;
            } else if(target > items[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] items = {1,2,3,4,5};
        System.out.println(new BinarySearch().search(items, 5));
    }
}
