package com.yukong.offer;

import javax.swing.tree.TreeNode;

/**
 * @author yukong
 * @date 2019-06-18 16:25
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
 * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，
 * 则重建二叉树并返回。
 */
public class Solution4 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {

        int pLen = pre.length;
        int iLen = in.length;
        if (pLen == 0 && iLen == 0) {
            return null;
        }


        return reBuild(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    public TreeNode reBuild(int[] pre, int pStart, int pEnd, int[] in, int iStart, int iEnd) {
        //建立根节点
        TreeNode tree = new TreeNode(pre[pStart]);
        tree.left = null;
        tree.right = null;
        if (pStart == pEnd && iStart == iEnd) {
            return tree;
        }

        // 找到中序遍历中的 root 节点
        int root = 0;
        for (root = iStart; root < pEnd; root++) {
            if (in[root] == tree.val) {
                break;
            }
        }
        // 左子树
        int leftTreeSize = root - iStart;
        // 右子树
        int rightTreeSize = iEnd - root;

        if (leftTreeSize > 0) {
            tree.left = reBuild(pre, pStart + 1, pStart + leftTreeSize, in, iStart, root - 1);
        }
        if (rightTreeSize > 0) {
            tree.right = reBuild(pre, pStart + 1 + leftTreeSize, pEnd, in, root + 1, iEnd);
        }
        return tree;
    }

}
