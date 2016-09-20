package com.jerry.proxy.dynamic;

/**
 * 测试类
 *
 * @author jerrychien
 * @create 2016-09-14 11:12
 */
public class TestCount {
    public static void main(String[] args) {
        CountImpl countImpl = new CountImpl();
        CountProxy proxy = new CountProxy(countImpl);
        proxy.queryCount();
        proxy.updateCount();
    }
}
