package com.jerry.proxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by jerrychien on 2017-03-28 17:18.
 * 此为代理类，用于在pointcut处添加advise
 */
public class CglibProxy implements MethodInterceptor {

    public CglibProxy() {

    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // 添加切面逻辑（advise），此处是在目标类代码执行之前，即为MethodBeforeAdviceInterceptor。
        System.out.println("before-------------");
        // 执行目标类add方法
        Object object = proxy.invokeSuper(obj, args);
        // 添加切面逻辑（advise），此处是在目标类代码执行之后，即为MethodAfterAdviceInterceptor。
        System.out.println("after--------------");
        return object;
    }

    public <T> T instance(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        //回调方法的参数为代理类对象CglibProxy，最后增强目标类调用的是代理类对象CglibProxy中的intercept方法
        enhancer.setCallback(this);
        // 此刻，而是增强过的目标类
        return (T) enhancer.create();
    }

}
