package com.yukong.concurrency.call;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author yukong
 * @date 2019-06-14 09:59
 */
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
        Thread thread = new Thread(futureTask);
        thread.start();
         while (!futureTask.isDone()){
             System.out.println("wait");
             TimeUnit.SECONDS.sleep(1);
         }
        System.out.println("完成。。。");
        System.out.println(futureTask.get());
    }

}


class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        return 520;
    }
}