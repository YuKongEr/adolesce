package com.yukong.datastructure.tree.bst;

import java.util.Stack;

/**
 * @author yukong
 * @date 2019-03-17 13:31
 * 二分搜索树
 */
public class BST<E extends Comparable<E>> {

    private class Node{
        /**
         * 元素值
         */
        public E val;

        /**
         * 左孩子
         */
        public Node left;

        /**
         * 右孩子
         */
        public Node right;

        public Node(E e){
            this.val = e;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * 根节点
     */
    private Node root;

    /**
     * 节点个数
     */
    private int size;

    public BST(){
        this.root = null;
        this.size = 0;
    }

    public int getSize(){
        return this.size;
    }

    public boolean isEmpty() {
        return this.size > 0;
    }

    /**
     * 添加
     * @param e
     */
    public void add(E e){
        this.root = add(this.root, e);
    }

    /**
     * 添加元素
     * 返回插入新节点后二分搜索树的根
     * @param node
     * @param e
     */
    private Node add(Node node, E e){
        if(node == null) {
            size ++;
            return new Node(e);
        }
        if(e.compareTo(node.val) < 0){
            node.left = add(node.left, e);
        } else if(e.compareTo(node.val) > 0){
            node.right = add(node.right ,e);
        }
        return node;
    }

    /**
     * 查看元素中是否包含元素e
     * @param e
     * @return
     */
    public boolean contains(E e){
        return contains(this.root, e);
    }

    /**
     * 查看以node为root查询是否包含e
     * @param node
     * @param e
     * @return
     */
    private boolean contains(Node node, E e) {
        if(node == null){
            return false;
        }
        if(e.compareTo(node.val) == 0){
            return true;
        } else if(e.compareTo(node.val) < 0){
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder(){
        preOrder(this.root);
        // 空行
        System.out.println();
    }


    private void preOrder(Node node){
        if(node ==  null){
            return;
        }
        System.out.print(node.val + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        inOrder(this.root);
        System.out.println("");
    }

    private void inOrder(Node node) {
        if(node ==  null){
            return;
        }
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }

    public void postOrder(){
        postOrder(this.root);
        System.out.println("");
    }

    private void postOrder(Node node){
        if(node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val + " ");

    }


    public void noRecPreOrder(){
        noRecPreOrder(this.root);
    }

    private void noRecPreOrder(Node node){
        Stack<Node> stack = new Stack<>();
    }
}
