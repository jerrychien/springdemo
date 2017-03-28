package com.jerry.proxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by jerrychien on 2017-03-28 17:23.
 */
public class Factory {
    /**
     * 泛型通了
     *
     * @param proxy
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T instance(CglibProxy proxy, Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        //回调方法的参数为代理类对象CglibProxy，最后增强目标类调用的是代理类对象CglibProxy中的intercept方法
        enhancer.setCallback(proxy);
        // 此刻，而是增强过的目标类
        return (T) enhancer.create();
    }
}
