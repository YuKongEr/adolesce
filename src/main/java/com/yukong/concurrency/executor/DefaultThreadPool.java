package com.yukong.concurrency.executor;

import javafx.concurrent.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yukong
 * @date 2018/9/5
 * @description
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    /**
     * 线程池最大数
     */
    private static final int MAX_WORKER_NUMBERS = 10;

    /**
     * 线程池默认数
     */
    private static final int DEFAULT_WORKER_NUMBERS = 5;

    /**
     * 线程池最小数
     */
    private static final int MIN_WORKER_NUMBERS = 1;

    /**
     * 任务队列
     */
    private final LinkedList<Job>  jobs = new LinkedList<>();

    /**
     * 工作者列表
     */
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    /**
     * 工作者线程数量
     */
    private int workerNum = DEFAULT_WORKER_NUMBERS;

    /**
     * 线程编号生成
     */
    private AtomicInteger threadNum = new AtomicInteger();

    public DefaultThreadPool(int workerNum) {
        this.workerNum = workerNum > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS
                : workerNum < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS
                : workerNum;
        initializeWorkers(this.workerNum);
    }

    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notifyAll();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker: workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
       synchronized (jobs) {
           // 限制新增的数目与已有的数目之和超过最大数
           if (num + this.workerNum> MAX_WORKER_NUMBERS) {
               num = MAX_WORKER_NUMBERS - this.workerNum;
           }
           initializeWorkers(num);
           this.workerNum += num;
       }
    }

    @Override
    public void removeWorks(int num) {
        synchronized (jobs) {
            if (num > this.workerNum) {
                throw new IllegalArgumentException("beyond workNum");
            }
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= num;
        }
    }

    @Override
    public int getJobCount() {
        return jobs.size();
    }

    class Worker implements Runnable {

        private volatile Boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            // 设置中断标记，让外界感知
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    job.run();
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
