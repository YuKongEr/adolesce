package com.yukong.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author yukong
 * @date 2018/9/6
 * @description
 */
public class MutexTest {

    private static Lock lock = new Mutex();
    private static Lock numLock = new Mutex();
    private static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        MutexTest mutexTest = new MutexTest();
        Thread t1 = new Thread(() -> {
            mutexTest.add(Thread.currentThread());
        });
        Thread t2 = new Thread(() -> {
            mutexTest.add(Thread.currentThread());
        });
        t1.start();
        t2.start();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                numLock.lock();
                numLock.lock();
                num++;
                numLock.unlock();
                numLock.unlock();
            });
        }
        TimeUnit.SECONDS.sleep(3);
        System.out.println(num);
    }


    public void add(Thread thread) {
        if (lock.tryLock()) {

            try {
                System.out.println(thread.getName() + "得到了锁");
            } catch (Exception e) {
            } finally {
                System.out.println(thread.getName() + "释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName() + "获取锁失败");
        }
    }

}
