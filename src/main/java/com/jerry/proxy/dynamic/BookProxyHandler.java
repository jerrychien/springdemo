package com.jerry.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jerrychien
 * @create 2016-09-20 18:18
 */
public class BookProxyHandler implements InvocationHandler {

    private Object target;

    public BookProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getDeclaringClass().getName() + "," + method.getName());
        System.out.println("start invoke");
        Object result = method.invoke(target, args);
        System.out.println("invoke over, result:" + result);
        return result;
    }

    public Object newProxyInstance() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }
}
