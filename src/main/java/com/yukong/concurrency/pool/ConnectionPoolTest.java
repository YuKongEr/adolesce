package com.yukong.concurrency.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yukong
 * @date 2018/9/5
 * @description 数据库连接池测试类
 */
public class ConnectionPoolTest {

    /**
     * 初始化数据库连接池，设置10个连接数
     */
    public static ConnectionPool pool = new ConnectionPool(10);

    /**
     * 保证所有线程一起开始执行
     */
    public static CountDownLatch start = new CountDownLatch(1);

    /**
     * main线程等待所有线程执行完毕，才执行。
     */
    public static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        // 线程数量
        int threadCount = 20;
        end = new CountDownLatch(threadCount);

        int count = 20;
        // 获取成功连接的次数
        AtomicInteger got = new AtomicInteger();
        // 获取失败连接的次数
        AtomicInteger notGot = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke" + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection: " + notGot);
    }

    static class ConnectionRunner implements Runnable {

        int count;

        AtomicInteger got;

        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {

            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (count > 0) {
                try {
                    // 从连接池获取连接，如果1000ms以内无法获取，将返回null
                    Connection connection = pool.fetchConnection(1000L);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();;
                        } finally {
                            // 释放连接
                            pool.releaseConnection(connection);
                            got.getAndIncrement();
                        }
                    } else {
                        notGot.getAndIncrement();
                    }
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
