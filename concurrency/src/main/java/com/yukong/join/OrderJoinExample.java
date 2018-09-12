package com.yukong.join;

/**
 * @author yukong
 * @date 2018/9/12
 * @description 保证A B C三个线程顺序执行。
 */
public class OrderJoinExample {

    public static void main(String[] args) throws InterruptedException {

        Thread ta = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " is running");
        }, "Thread-A");
        Thread tb = new Thread(()->{
            try {
                ta.join();
                System.out.println(Thread.currentThread().getName() + " is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-B");
        Thread tc = new Thread(()->{
            try {
                tb.join();
                System.out.println(Thread.currentThread().getName() + " is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-C");
        tc.start();
        tb.start();
        ta.start();
        tc.join();
        System.out.println(Thread.currentThread().getName() + " is running");
    }

}
