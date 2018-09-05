package com.yukong.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author yukong
 * @date 2018/9/5
 * @description 数据库连接池
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    /**
     * 释放连接
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }
    /**
     * 获取连接
     * @param mills 超时等待时间
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 判断mills是否为正整数
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                // 等待结束的时间
                long future = System.currentTimeMillis() + mills;
                // 剩余的时间
                long remaining = mills;
                while (pool.isEmpty() && remaining >0) {
                    pool.wait();
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
