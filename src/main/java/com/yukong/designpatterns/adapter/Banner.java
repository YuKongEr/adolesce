package com.yukong.designpatterns.adapter;

/**
 * @author: yukong
 * @date: 2018/12/25 14:07
 * 被适配的对象
 */
public class Banner {

    public Banner(String string) {
        this.string = string;
    }

    private String string;

    public void showWithParen() {
        System.out.println("(" + string + ")");
    }

    public void showWithAster() {
        System.out.println("*" + string + "*");
    }

}
