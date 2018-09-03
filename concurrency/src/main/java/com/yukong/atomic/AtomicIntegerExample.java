package com.yukong.atomic;

import com.yukong.annoations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yukong
 * @date 2018/8/29
 * @description
 */
@ThreadSafe
public class AtomicIntegerExample {

    /**
     * 并发线程数目
     */
    private static int threadNum = 1000;

    /**
     * 闭锁
     */
    private static CountDownLatch countDownLatch  = new CountDownLatch(threadNum);

    private static AtomicInteger i = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int j = 0; j < threadNum; j++) {
            executorService.execute(() -> {
                add();
            });
        }
        // 使用闭锁保证当所有统计线程完成后，主线程输出统计结果。 其实这里也可以使用Thread.sleep() 让主线程阻塞等待一会儿实现
        countDownLatch.await();
        System.out.println(i.get());
    }

    private static void add() {
        countDownLatch.countDown();
        i.getAndIncrement();
    }

}
