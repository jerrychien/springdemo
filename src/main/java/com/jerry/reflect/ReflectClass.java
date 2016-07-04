package com.jerry.reflect;

/**
 * ReflectClass
 *
 * @author jerrychien
 * @create 2016-07-01 14:23
 */
public class ReflectClass implements ReflectInterface {

    private ReflectClass() {
        System.out.println("private constructor without param");
    }

    private ReflectClass(Integer num) {
        System.out.println("private constructor with Integer param [" + num + "]");
    }

    public ReflectClass(String name) {
        System.out.println("pubilc constructor with string param {" + name + "}");
    }

    @Override
    public void doPrint() {
        System.out.println("doPrint without params");
    }

    @Override
    public void doPrint(Integer num) {
        System.out.println("doPrint with one int params [" + num + "]");
    }

    @Override
    public void doPrint(String content) {
        System.out.println("doPrint with one string param [" + content + "]");
    }

    @Override
    public void doPrint(String content, String msg) {
        System.out.println("doPrint with two string param [" + content + "][" + msg + "]");
    }

    private void privatePrint(String name) {
        System.out.println("private print with one string param  [" + name + "]");
    }
}
