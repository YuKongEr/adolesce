package com.yukong.designpatterns.template;

/**
 * @author: yukong
 * @date: 2018/12/25 15:25
 * 具体实现类
 */
public class CharDisplay extends AbstractDisplay{


    private char ch;

    public CharDisplay(char ch) {
        this.ch = ch;
    }

    @Override
    public void open() {
        System.out.print("<<");
    }

    @Override
    public void print() {
        System.out.print(ch);
    }

    @Override
    public void close() {
        System.out.println(">>");
    }
}
