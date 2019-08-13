package com.yukong.offer;

/**
 * @author yukong
 * @date 2019-06-18 15:01
 * 题目描述
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 */
public class Solution2 {

    public String replaceSpace(StringBuffer str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ' '){
                sb.append("  ");
            }
        }
        int p = str.length() - 1;
        int q = sb.length() - 1;
        while (p >= 0){
            char c = sb.charAt(p--);
            if (c == ' ') {
                sb.setCharAt(q--, '0');
                sb.setCharAt(q--, '2');
                sb.setCharAt(q--, '%');
            } else {
                sb.setCharAt(q--, c);
            }
        }
        return sb.toString();
    }

}
