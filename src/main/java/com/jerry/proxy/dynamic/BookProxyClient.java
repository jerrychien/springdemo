package com.jerry.proxy.dynamic;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @author jerrychien
 * @create 2016-09-20 16:10
 */
public class BookProxyClient {
    public static void main(String[] args) {
        BookFacade bookFacade = new BookFacadeImpl();
        BookProxyHandler myInvoke = new BookProxyHandler(bookFacade);
        BookFacade proxyBookFacade = (BookFacade) myInvoke.newProxyInstance();
        System.out.println(proxyBookFacade.getClass().getName());
        proxyBookFacade.addBook();
        proxyBookFacade.addBook("bill Gates");
        createProxyClassFile();
    }

    public static void createProxyClassFile() {
        String name = "/Users/jerrychien/Documents/ProxyBookFacade";
        byte[] data = ProxyGenerator.generateProxyClass("ProxyBookFacade", new Class[]{BookFacade.class});
        try {
            FileOutputStream out = new FileOutputStream(name + ".class");
            out.write(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
