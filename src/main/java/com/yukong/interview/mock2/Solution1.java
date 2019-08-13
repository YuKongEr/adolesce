package com.yukong.interview.mock2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yukong
 * @date 2019-06-12 16:20
 * 栈实现
 */
public class Solution1 {


    public String simplifyPath(String path) {
        String[] paths = path.split("\\/");
        List<String> list = new ArrayList<>(paths.length);
        for (String string : paths) {
            if (string.equals(".") || string.equals("")) {
               continue;
            } else if (string.equals("..")) {
                // 删掉最后一个
                int size = list.size();
                if (size > 0) {
                    list.remove(size - 1);
                }
            } else {
                // 加到最后一个
                list.add(string);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (!s.equals("")) {
                stringBuilder.append(s).append("/");
                // result = result + s + "/";
            }
        }
        int length = stringBuilder.length();
        if (length == 0) {
            // 这种情况, 要么是/ 要么是.
            if (path.charAt(0) == '/') {
                return "/";
            } else {
                return ".";
            }
        } else {
            String s = stringBuilder.deleteCharAt(length - 1).insert(0, "/").toString();
            return s;
        }
    }

}
