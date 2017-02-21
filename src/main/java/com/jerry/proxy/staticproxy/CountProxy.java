package com.jerry.proxy.staticproxy;

/**
 * Created by jerrychien on 2017-02-21 18:55.
 */
public class CountProxy implements Count {

    private Count countImpl;

    public CountProxy(Count countImpl) {
        this.countImpl = countImpl;
    }

    @Override
    public void queryCount() {
        System.out.println("before queryCount");
        countImpl.queryCount();
        System.out.println("after queryCount");
    }

    @Override
    public void updateCount() {
        System.out.println("before updateCount");
        countImpl.updateCount();
        System.out.println("after updateCount");
    }
}
