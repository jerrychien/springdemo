package com.jerry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类注解
 *
 * @author jerrychien
 * @create 2016-07-13 14:33
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyClassAnnotation {

    String uri() default "default class uri";

    String desc() default "default class desc";
}
