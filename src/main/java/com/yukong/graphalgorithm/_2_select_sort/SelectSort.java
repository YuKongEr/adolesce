package com.yukong.graphalgorithm._2_select_sort;

/**
 * @author yukong
 * @date 2019-05-14 20:32
 */
public class SelectSort {

    public int[] sort(int[] items){
        for (int i = 0; i < items.length - 1; i++) {
            int k = i;
            for (int j = i + 1; j < items.length; j++) {
                // 找到比i大的数
                if(items[j] < items[k]){
                    k = j;
                }
            }
            // 如果找到了 则交换
            if(i != k){
                int temp = items[i];
                items[i] = items[k];
                items[k] = temp;
            }
        }
        return items;
    }


    public static void main(String[] args) {
        int[] items = {1,4,2,3,5,6};
        new SelectSort().sort(items);
        for (int item : items) {
            System.out.print(item + " ");
        }
    }
}
