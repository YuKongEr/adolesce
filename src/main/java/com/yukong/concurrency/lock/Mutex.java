package com.yukong.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author yukong
 * @date 2018/9/6
 * @description 独占锁（排它锁）
 */
public class Mutex implements Lock {

    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            Thread thread = Thread.currentThread();
            int c = getState();
            if(c == 0) {
                // 获取锁，cas设置state为1
                if (!hasQueuedPredecessors() && compareAndSetState(0, 1)) {
                    // 设置占用排它锁的线程是当前线程
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }
            // 可重入锁的实现
            if (thread == getExclusiveOwnerThread()) {
                int nextc = c + arg;
                if (nextc < 0) {
                    throw new Error("max lock not expect ");
                }
                compareAndSetState(c, nextc);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {

            // 如果没有被占用
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }
            boolean isFree = false;
            int next = getState() - arg;
            if (next == 0) {
                // 取消排它锁的线程
                setExclusiveOwnerThread(null);
                isFree = true;
            }
            setState(next);
            return isFree;
        }

        @Override
        protected boolean isHeldExclusively() {
            // 是否存于占用状态
            return super.getState() == 1;
        }
   }

    private final static Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.new ConditionObject();
    }
}
