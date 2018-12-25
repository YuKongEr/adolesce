package com.yukong.designpatterns.iterator;

/**
 * @author: yukong
 * @date: 2018/12/25 11:26
 * 表示集合的接口
 */
public interface Aggregate {

    /**
     * 获取集合迭代器
     * @return
     */
    Iterator iterator();

}
