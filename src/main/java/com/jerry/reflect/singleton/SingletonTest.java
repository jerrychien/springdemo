package com.jerry.reflect.singleton;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jerrychien on 2017-02-21 15:46.
 */
public class SingletonTest {
    public static void main(String[] args) {
        //使用反射调用private的构造方法
        Class clazz = null;
        try {
            clazz = Class.forName("com.jerry.reflect.singleton.Singleton");
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);//private方法这个必须
            Singleton singleton1 = (Singleton) constructor.newInstance();
            System.out.println("singleton1：" + singleton1);
            Singleton singleton2 = Singleton.getSingleton();
            System.out.println("singleton2：" + singleton2);
            Singleton singleton3 = Singleton.getSingleton();
            System.out.println("singleton3：" + singleton3);
            System.out.println("is Singlten equals :" + (singleton1 == singleton2));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("what the fuck");
        }
    }
}
