package com.yukong.datastructure.tree.bst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yukong
 * @date 2019-03-17 14:15
 */
public class Main {

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }
        System.out.println("前序遍历：");
        bst.preOrder();
        System.out.println("中序遍历：");
        bst.inOrder();
        System.out.println("后序遍历：");
        bst.postOrder();

        System.out.println(3 * 3.14d);
        System.out.println( 3.14 * 3 * 3);
    }

}
