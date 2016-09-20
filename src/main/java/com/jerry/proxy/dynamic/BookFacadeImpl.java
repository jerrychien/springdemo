package com.jerry.proxy.dynamic;

/**
 * @author jerrychien
 * @create 2016-09-14 15:19
 */
public class BookFacadeImpl implements BookFacade {
    @Override
    public void addBook() {
        System.out.println("……增加图书的方法……");
    }

    @Override
    public String addBook(String name) {
        return name;
    }
}
