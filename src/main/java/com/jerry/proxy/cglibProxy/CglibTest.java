package com.jerry.proxy.cglibProxy;

/**
 * Created by jerrychien on 2017-03-28 17:29.
 */
public class CglibTest {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        Base base = Factory.instance(cglibProxy, Base.class);
        base.add();
    }
}
