package com.yukong.atomic;

import com.yukong.annoations.NotThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yukong
 * @date 2018/8/29
 * @description 线程不安全
 */
@NotThreadSafe
public class CountExample {

    /**
     * 并发线程数目
     */
    private static int threadNum = 1000;

    /**
     * 闭锁
     */
    private static CountDownLatch countDownLatch  = new CountDownLatch(threadNum);

    private static Integer i = 0;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int j = 0; j < threadNum; j++) {
            executorService.execute(() -> {
                add();
            });
        }
        // 使用闭锁保证当所有统计线程完成后，主线程输出统计结果。 其实这里也可以使用Thread.sleep() 让主线程阻塞等待一会儿实现
        countDownLatch.await();
        System.out.println(i);
    }

    private static void add() {
        countDownLatch.countDown();
        i++;
    }

}
