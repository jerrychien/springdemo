package com.jerry.proxy.cglibProxy;

/**
 * Created by jerrychien on 2017-03-28 17:17.
 * 被代理类，即目标对象target
 */
public class Base {
    /**
     * 一个模拟的add方法
     */
    public void add() {
        System.out.println("Base add!");
    }
}
