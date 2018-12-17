package com.yukong.concurrency.volatiles;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yukong
 * @date 2018/8/31
 * @description 重排序测试
 */
public class ReSortExample {

    //测试的信号量
    private static int a, b, x, y;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
           Thread t1 = new Thread(() ->{
               a = 1;
               x = b;
           });
            Thread t2 = new Thread(() ->{
                b = 2;
                y = a;
            });
            // 启动线程
           t1.start();
           t2.start();
           // 主线程等待两个线程完成
           t1.join();
           t2.join();
            System.out.println("[" + i+"] x =" + x + " y =" + y);
        }
    }

}
