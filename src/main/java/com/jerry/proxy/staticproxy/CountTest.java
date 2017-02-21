package com.jerry.proxy.staticproxy;

/**
 * Created by jerrychien on 2017-02-21 18:57.
 */
public class CountTest {
    public static void main(String[] args) {
        Count countImpl = new CountImpl();
        CountProxy proxy = new CountProxy(countImpl);
        proxy.updateCount();
        proxy.queryCount();
    }
}
