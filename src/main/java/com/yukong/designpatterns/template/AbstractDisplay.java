package com.yukong.designpatterns.template;

/**
 * @author: yukong
 * @date: 2018/12/25 15:22
 * 抽象类
 */
public abstract class AbstractDisplay {

    /**
     * 给子类实现的抽象方法  open
     */
    public abstract void open();

    /**
     * 给子类实现的抽象方法  print
     */
    public abstract void print();

    /**
     * 给子类实现的抽象方法  close
     */
    public abstract void close();

    public void display() {
        open();
        for (int i = 0; i < 5 ; i++) {
            print();
        }
        close();
    }

}
