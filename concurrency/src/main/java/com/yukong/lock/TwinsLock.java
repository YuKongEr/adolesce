package com.yukong.lock;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author yukong
 * @date 2018/9/10
 * @description 同步工具类，只允许最多两个线程被访问
 */
public class TwinsLock implements Lock {

    private static final Sync sync = new Sync();


    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0 ? true : false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw   new UnsupportedOperationException();
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    final static class Sync extends AbstractQueuedSynchronizer {

        protected Sync() {
            super.setState(2);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (;;) {
                int newCount = getState() - reduceCount;
                if (newCount < 0 || compareAndSetState(getState(), newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (;;) {
                int newCount = getState() + returnCount;
                if (compareAndSetState(getState(), newCount)) {
                    return true;
                }
            }
        }

        @Override
        protected boolean isHeldExclusively() {
            return super.isHeldExclusively();
        }
    }
}
