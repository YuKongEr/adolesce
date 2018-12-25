package com.yukong.designpatterns.adapter.entrust;

import com.yukong.designpatterns.adapter.Banner;

import java.util.HashMap;

/**
 * @author: yukong
 * @date: 2018/12/25 14:19
 */
public class Main {

    public static void main(String[] args) {
        Print print = new PrintBanner(new Banner("hello"));
        print.printStrong();
        print.printWeak();
    }

}
