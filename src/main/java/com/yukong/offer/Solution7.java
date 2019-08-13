package com.yukong.offer;

/**
 * @author yukong
 * @date 2019-06-20 16:12
 * 一只青蛙一次可以跳上1级台阶，
 * 也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 */
public class Solution7 {

    public int JumpFloor(int target) {
        int n1 = 1;
        int n2 = 2;
        int n;
        if(target == 1){
            return n1;
        }
        if(target == 2){
            return n2;
        }
        int fib = 0;
        for (int i = 3; i <= target ; i++) {
            fib = n1 + n2;
            n1 = n2;
            n2 = fib;
        }
        return fib;
    }

    public static void main(String[] args) {
        System.out.println(new Solution7().JumpFloor(4));
    }
}
