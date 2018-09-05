package com.yukong.interview.concurrency.chapter1;

/**
 * @author yukong
 * @date 2018/9/5
 * @description
 */
public class TaskRunner1 implements Runnable {
    @Override
    public void run() {
        System.out.println( " implement runnable ");
    }


    public static void main(String[] args) {
        new Thread(new TaskRunner1()).start();;
    }
}
