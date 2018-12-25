package com.yukong.designpatterns.iterator;

/**
 * @author: yukong
 * @date: 2018/12/25 11:34
 * 书架迭代器类
 */
public class BookShelfIterator implements Iterator<Book>{

    private BookShelf bookShelf;

    private Integer index;


    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if(index < bookShelf.getLength()) {
            return true;
        }
        return false;
    }

    @Override
    public Book next() {
        return bookShelf.getBookAt(index++);
    }

}
