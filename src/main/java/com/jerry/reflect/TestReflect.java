package com.jerry.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * TestReflect
 *
 * @author jerrychien
 * @create 2016-07-01 14:25
 */
public class TestReflect {
    public static void main(String[] args) {
        try {
            //类装载和连接操作
            String className = "com.jerry.reflect.ReflectClass";
            Class clazz = Class.forName(className);

            Class intclass = int.class;
            Class longclass = long.class;
            System.out.println(longclass.getName());
            System.out.println(intclass == longclass);

            //利用反射获取私有构造方法(不带参数的私有构造方法)
            //Constructor constructor = clazz.getDeclaredConstructor();
            //constructor.setAccessible(true);//这个必须
            //ReflectInterface o = (ReflectInterface) constructor.newInstance();

            //利用反射获取私有构造方法(带参数的私有构造方法)
            Constructor constructor = clazz.getDeclaredConstructor(Integer.class);
            constructor.setAccessible(true);//这个必须
            ReflectInterface o = (ReflectInterface) constructor.newInstance(2);

            //代码中只定义接口ReflectInterface,具体的实现各自去实现,className从配置文件或者流中读取,做到大大的解耦
            //只有public的无参数构造函数才能使用
            //ReflectInterface o = (ReflectInterface) clazz.newInstance();

            //public method
            Method noParamMethod = clazz.getMethod("doPrint");
            Method oneIntParamMethod = clazz.getMethod("doPrint", Integer.class);
            Method oneStringParamMethod = clazz.getMethod("doPrint", String.class);
            Method twoStringParamMethod = clazz.getMethod("doPrint", String.class, String.class);

            //private method
            Method privateMethod = clazz.getDeclaredMethod("privatePrint", String.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(o, "privateMethod");

            //public method
            noParamMethod.invoke(o);
            oneIntParamMethod.invoke(o, 8888);
            oneStringParamMethod.invoke(o, "你好");
            twoStringParamMethod.invoke(o, "你好", "你好啊");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
