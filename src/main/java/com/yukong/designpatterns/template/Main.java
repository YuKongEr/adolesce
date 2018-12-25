package com.yukong.designpatterns.template;

/**
 * @author: yukong
 * @date: 2018/12/25 15:30
 */
public class Main {

    public static void main(String[] args) {
        AbstractDisplay d1 = new CharDisplay('B');

        AbstractDisplay d2 = new StringDisplay("Java");

        AbstractDisplay d3 = new StringDisplay("Go");

        d1.display();
        d2.display();
        d3.display();
    }

}
