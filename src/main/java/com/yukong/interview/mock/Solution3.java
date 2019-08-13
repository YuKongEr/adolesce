package com.yukong.interview.mock;

import java.util.Arrays;

/**
 * @author yukong
 * @date 2019-06-12 15:31
 * 1）如果一个长度为n的数组可以被划分为A和B两个数组，我们假设A的长度小于B并且A的大小是k，
 * 那么：total_sum / n == A_sum / k == B_sum / (n - k)，其中1 <= k <= n / 2。
 * 那么可以知道：A_sum = total_sum * k / n。
 * 由于A_sum一定是个整数，所以我们可以推导出total_sum * k % n == 0，
 * 那就是说，对于特定的total_sum和n而言，符合条件的k不会太多。
 * 这样我们在第一步中就首先验证是否存在符合条件的k，如果不存在就可以提前返回false。
 *
 * 2）如果经过第一步的验证，发现确实有符合条件的k，
 * 那么我们在第二步中，就试图产生k个子元素的所有组合，
 * 并且计算他们的和。这里的思路就有点类似于背包问题了，
 * 我们的做法是：定义vector<vector<unordered_set<int>>> sums，
 * 其中sums[i][j]表示A[0, i]这个子数组中的任意j个元素的所有可能和。
 * 可以得到递推公式是：sums[i][j] = sums[i - 1][j] "join" (sums[i][j - 1] + A[i])，
 * 其中等式右边的第一项表示这j个元素中不包含A[i]，而第二项表示这j个元素包含A[i]。
 * 这样就可以采用动态规划的思路得到sums[n - 1][k]了（1 <= k <= n / 2）。
 *
 * 3）有了sums[n - 1][k]，我们就检查sums[n - 1][k]中是否包含(total_sum * k / n)。
 * 一旦发现符合条件的k，就返回true，否则就返回false。
 *
 * 在递推公式中我们发现，sums[i][j]仅仅和sums[i - 1][j]，sums[i][j - 1]有关，
 * 所以可以进一步将空间复杂度从O(n^2*M)降低到O(n*M)，其中M是n中的所有元素的组合数（可能高达O(2^n)）。时间复杂度为O(n^3*M)。

 */
public class Solution3 {

    //np问题，不能暴搜
    public boolean splitArraySameAverage(int[] A) {
        int len = A.length;
        //sum为总和
        int sum = 0;
        for(int i = 0; i < len; i++){
            sum += A[i];
        }
        Arrays.sort(A);
        //从小到大排序，从0开始处理较少数据
        for(int i = 1; i <= len/2; i++){
            int remainder = sum * i % len;
            //首先确认能否整除
            int target = sum * i / len;
            //这是目标和
            if(remainder == 0 && helper(A,0,i,target)) {
                return true;
            }
        }
        return false;
    }

    public boolean helper(int[] A, int begin, int len, int target){
        if(len == 0) {
            return target == 0;
        }
        if(target < len * A[begin]) {
            return false;
        }
        for(int i = begin; i <= A.length - len; i++){
            //略去重复的情况，缩短运行时间
            if(i > begin && A[i] == A[i-1]) {
                continue;
            }
            if(helper(A, i+1, len-1, target- A[i])) {
                return true;
            }
        }
        return false;
    }

}
