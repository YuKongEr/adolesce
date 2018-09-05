package com.yukong.interview.concurrency.chapter1;

/**
 * @author yukong
 * @date 2018/9/5
 * @description
 */
public class TaskThread1 extends Thread{

    @Override
    public void run() {
        System.out.println("extend Thread");
    }

    public static void main(String[] args) {
        new TaskThread1().start();
    }
}
