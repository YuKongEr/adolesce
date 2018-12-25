package com.yukong.designpatterns.iterator;

/**
 * @author: yukong
 * @date: 2018/12/25 11:26
 * 遍历集合的接口
 */
public interface Iterator<T> {
    /**
     * 判断是否有下一个元素
     * @return
     */
    boolean hasNext();

    /**
     * 返回下一个元素
     * @return
     */
    T next();
}
