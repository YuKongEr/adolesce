package com.yukong.interview.concurrency.chapter1;

import java.util.concurrent.*;

/**
 * @author yukong
 * @date 2018/9/5
 * @description
 */
public class TaskExecutor {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20,
                1000L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024));
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println("by thread pool");
            });
        }
    }

}
