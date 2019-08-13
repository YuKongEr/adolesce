package com.yukong.daily;

import java.util.Arrays;

/**
 * @author yukong
 * @date 2019-06-27 12:39
 * 加入给你m个宠物 n对朋友关系 求朋友圈的个数
 * 比如 小猫1，小猫2，小猫3，小狗4，小狗5 再告诉你小猫1和小狗4是好朋友，
 * 所以 1 4 合并集合（1，4）小猫2和小狗4是好朋友 所以 2与（1 4）
 * 合并集合 得到（1，2，4），小猫3和小狗5是好朋友 所以 得到集合（3，5）。 最后 变成了(1,2,4) (3,5)两个集合
 */
public class FriendMoments {


    public static void main(String[] args) {
        UnionSet set = new UnionSet(5);

        set.union(0,1);
        set.union(0,3);
        set.union(2,4);

        System.out.println(set.count());
    }

}

class UnionSet{
    private int[]  ele;

    private int[] height;

    public UnionSet(int nums) {
        ele = new int[nums];
        height = new int[nums];
        Arrays.fill(ele, -1);
        Arrays.fill(height, 1);
    }


    /**
     * 两个朋友圈合并
     * @param x
     * @param y
     */
    public void union(int x, int y) {
        int rootX = getRoot(x);
        int rootY = getRoot(y);
        // 如果不是同一个根就连起来
        if(rootX != rootY) {
            if(height[rootX] < height[rootY]) {
                ele[rootX] = rootY;
            } else if(height[rootY] < height[rootX]) {
                ele[rootY] = rootX;
            } else {
                ele[rootY] = rootX;
                height[rootX]++;
            }
        }
    }

    /**
     * 找到当前元素所在朋友圈的根
     * @param x
     * @return
     */
    public int getRoot(int x) {
        while (ele[x] != -1) {
            x = ele[x];
        }
        return x;
    }

    /**
     * 计算形成了多少颗树
     * @return
     */
    public int count() {
        int count = 0;
        for(int i=0; i<ele.length; i++) {
            if(ele[i] == -1) {
                count++;
            }
        }
        return count;
    }

    /**
     * 打印并查集
     */
    public void print() {
        for(int i=0; i<ele.length; i++) {
            System.out.print(ele[i] + " ");
        }
        System.out.println();
    }
}
