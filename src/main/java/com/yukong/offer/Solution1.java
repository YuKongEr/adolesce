package com.yukong.offer;

/**
 * @author yukong
 * @date 2019-06-18 14:56
 * 题目描述
 * 在一个二维数组中（每个一维数组的长度相同），
 * 每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class Solution1 {

    public boolean find(int target, int [][] array) {
        int rows = array.length - 1, cols = 0;
        while (rows < array.length && rows >= 0 && cols < array[0].length && cols >= 0) {
            if (array[rows][cols] == target) {
                return true;
            } else if (array[rows][cols] < target) {
                cols++;
            } else if (array[rows][cols] > target) {
                rows--;
            }
        }
        return false;
    }
}
