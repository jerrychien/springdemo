package com.jerry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性注解
 *
 * @author jerrychien
 * @create 2016-07-13 14:37
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyFiledAnnotation {
    String uri();

    String desc();
}
