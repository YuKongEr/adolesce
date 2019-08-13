package com.yukong.interview.mock;

/**
 * @author yukong
 * @date 2019-06-12 14:45
 */
public class Solution1 {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        arr = plusOne(arr);
        System.out.println(arr);
    }


    public static int[] plusOne(int[] digits) {
        if(digits == null || digits.length ==0){
            int[] res = {0};
            return res;
        }
        if(digits[0] == 0){
            int[] res = {1};
            return res;
        }

        // 最后一位不为9
        if(digits[digits.length - 1] < 9){
            digits[digits.length - 1] += 1;
            return digits;
        }

        // 判断是不是9999的情况
        boolean addFlag = true;
        for (int i = 0; i < digits.length; i++) {
            if(digits[i] != 9){
                addFlag = false;
                break;
            }
        }
        // 如果都是999
        if(addFlag){
            int[] res = new int[digits.length + 1];
            for (int i = 0; i < digits.length + 1; i++) {
                if(i == 0){
                    res[i] = 1;
                } else {
                    res[i] = 0;
                }
            }
            return res;
        }

        //普通情况

        int i = digits.length-1;
        // 声明一个新的数组
        int[] array = new int[digits.length];
        while(digits[i] == 9){
            // 如果是最后一位 只是进位
            array[i] = 0;
            array[i-1] = digits[i-1]+1;
            i--;
        }
        // 判断如果补位没补完，把剩下的位数补充完整
        if(i == 0 && digits.length>1 && digits[1] != 9){
            array[i] = digits[i];
        }
        if(i>0){
            for(int m = 0;m<i; m++){
                array[m] = digits[m];
            }
        }
        return array;
    }

}
