package com.yukong.offer;

import java.util.Stack;

/**
 * @author yukong
 * @date 2019-06-18 16:46
 * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 */
public class Solution5 {

    Stack<Integer> in = new Stack<Integer>();
    Stack<Integer> out = new Stack<Integer>();

    public void push(int node) {
        in.push(node);
    }

    public int pop() throws Exception {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }

        if (out.isEmpty()) {
            throw new Exception("queue is empty");
        }

        return out.pop();
    }




}
