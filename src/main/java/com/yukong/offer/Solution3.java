package com.yukong.offer;

import java.util.ArrayList;

/**
 * @author yukong
 * @date 2019-06-18 15:14
 * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
 */
public class Solution3 {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> ret = new ArrayList<>();
        if (listNode != null) {
            ret.addAll(printListFromTailToHead(listNode.next));
            ret.add(listNode.val);
        }
        return ret;
    }

}
