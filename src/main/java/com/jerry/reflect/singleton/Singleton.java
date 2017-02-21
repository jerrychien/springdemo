package com.jerry.reflect.singleton;

/**
 * Created by jerrychien on 2017-02-21 15:41.
 */
public class Singleton {

    private static int count = 0;

    //private static Singleton singleton = new Singleton();

    //只有自己抛出异常才行，直接在里面try catch貌似还可以生成对象，有点不解
    //防止使用反射二次创建对象,我感觉很重要
    private Singleton() throws Exception {
        if (count != 0) {
            throw new Exception("对象无法再次创建");
        }
        if (count == 0) {
            System.out.println("初次创建对象");
        } else {
            System.out.println("创建对象" + (count + 1));
        }
        count++;
    }

    private static class SingletonHelper {
        private static Singleton singleton;

        static {
            try {
                singleton = new Singleton();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Singleton getSingleton() {
        return SingletonHelper.singleton;
    }
}
