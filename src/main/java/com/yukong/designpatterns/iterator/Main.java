package com.yukong.designpatterns.iterator;

/**
 * @author: yukong
 * @date: 2018/12/25 11:39
 */
public class Main {

    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(4);

        bookShelf.appendBook(new Book("java"));
        bookShelf.appendBook(new Book("php"));
        bookShelf.appendBook(new Book("go"));
        bookShelf.appendBook(new Book("js"));

        Iterator<Book> iterator = bookShelf.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(book.getName());
        }
    }
}
