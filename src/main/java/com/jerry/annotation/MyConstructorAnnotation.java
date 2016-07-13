package com.jerry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 构造方法注解
 *
 * @author jerrychien
 * @create 2016-07-13 14:29
 */
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyConstructorAnnotation {

    String uri();

    String desc();
}
