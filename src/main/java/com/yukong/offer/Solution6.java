package com.yukong.offer;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yukong
 * @date 2019-06-20 15:04
 * 把一个数组最开始的若干个元素搬到数组的末尾，
 * 我们称之为数组的旋转。 输入一个非减排序的数组的一个旋转，
 * 输出旋转数组的最小元素。
 * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。 NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
 */
public class Solution6 {

    public int minNumberInRotateArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int m = l + (h - l) / 2;
            if (nums[l] == nums[m] && nums[m] == nums[h]) {
                return minNumber(nums, l, h);
            } else if (nums[m] <= nums[h]) {
                h = m;
            } else {
                l = m + 1;
            }
        }
        return nums[l];
    }

    private int minNumber(int[] nums, int l, int h) {
        for (int i = l; i < h; i++) {
            if (nums[i] > nums[i + 1]) {
                return nums[i + 1];
            }
        }
        return nums[l];
    }

    public static void main(String[] args) {
        Map<String, String > map = new HashMap<>(16);
        map.put("key1", "value1");
        map.put("key2", "value2");
        StringBuilder messageBuilder = new StringBuilder();
        map.entrySet().stream().forEach(entry -> {
            messageBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        });
        String message = messageBuilder.substring(0, messageBuilder.length() - 1);
        System.out.println(message);

    }

}
