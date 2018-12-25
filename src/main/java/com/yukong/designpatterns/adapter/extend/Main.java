package com.yukong.designpatterns.adapter.extend;

/**
 * @author: yukong
 * @date: 2018/12/25 14:12
 */
public class Main {

    public static void main(String[] args) {
        Print print = new PrintBanner("hello");
        print.printStrong();
        print.printWeak();
    }

}
