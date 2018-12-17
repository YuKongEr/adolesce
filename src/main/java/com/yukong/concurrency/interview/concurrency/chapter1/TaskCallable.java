package com.yukong.concurrency.interview.concurrency.chapter1;

import java.util.concurrent.*;

/**
 * @author yukong
 * @date 2018/9/5
 * @description
 */
public class TaskCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        String result = "implement Callable";
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new TaskCallable());
        new Thread(futureTask).start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(futureTask.get());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new TaskCallable());
        System.out.println(future.get());;
    }
}
