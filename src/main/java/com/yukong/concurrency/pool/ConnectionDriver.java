package com.yukong.concurrency.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @author yukong
 * @date 2018/9/5
 * @description 获取数据库连接
 */
public class ConnectionDriver {

    /**
     * 生成connection代理类
     * 通过jdk动态代理
     */
    static class ConnectionHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("commit")) {
                TimeUnit.MILLISECONDS.sleep(100L);
            }
            return null;
        }
    }

    /**
     * 获取连接代理类
     * @return
     */
    public static final Connection createConnection() {

        return (Connection) Proxy.newProxyInstance(ConnectionHandler.class.getClassLoader(),
                new Class<?>[]{Connection.class},new ConnectionHandler());
    }

}
