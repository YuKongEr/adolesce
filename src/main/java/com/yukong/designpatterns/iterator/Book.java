package com.yukong.designpatterns.iterator;

/**
 * @author: yukong
 * @date: 2018/12/25 11:30
 */
public class Book {

    private String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
