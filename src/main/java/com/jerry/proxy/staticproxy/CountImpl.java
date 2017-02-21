package com.jerry.proxy.staticproxy;

/**
 * Created by jerrychien on 2017-02-21 18:53.
 */
public class CountImpl implements Count {

    @Override
    public void queryCount() {
        System.out.println("countmpl queryCount");
    }

    @Override
    public void updateCount() {
        System.out.println("countimpl updateCount");
    }
}
