package com.yukong.concurrency.executor;

/**
 * @author yukong
 * @date 2018/9/5
 * @description 线程池接口，抽象出来，定义规范
 */
public interface ThreadPool<Job extends Runnable> {

    /**
     * 执行任务，这个任务需要继承Runnable接口
     * @param job 任务
     */
    void execute(Job job);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 添加工作者数目
     * @param num 要添加的数量
     */
    void addWorkers(int num);

    /**
     * 减少工作者数目
     * @param num 要减少的数量
     */
    void removeWorks(int num);

    /**
     * 获取正在等待执行的任务数量
     * @return
     */
    int getJobCount();
}
