package com.yukong.concurrency.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yukong
 * @date 2018/9/12
 * @description 有界阻塞队列 线程安全的
 */
public class BoundedQueue<T> {

    /**
     * 成员数组
     */
    private Object[] items;

    /**
     * 队尾下标 队头下标 总数
     */
    private int addIndex, removeIndex, count;

    private Lock lock = new ReentrantLock();

    /**
     * 出队条件
     */
    private Condition notEmpty = lock.newCondition();

    /**
     * 入队条件
     */
    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size) {
        this.items =  new Object[size];
    }

    /**
     * 入队
     * @param t
     * @throws InterruptedException
     */
    private void add(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count== items.length) {
                notFull.await();
            }
            items[addIndex++] = t;
            ++count;
            if (addIndex == items.length) {
                addIndex = 0;
            }
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 出队
     * @return
     * @throws InterruptedException
     */
    public T remove() throws InterruptedException {
        lock.lock();
        T result = null;
        try {
            while (count <= 0) {
                notEmpty.await();
            }
            result = (T) items[removeIndex++];
            count--;
            if (removeIndex == items.length) {
                removeIndex = 0;
            }
            return result;
        } finally {
            notFull.signal();
        }
    }
}
