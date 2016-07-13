package com.jerry.inteceptor;

import com.jerry.annotation.MyClassAnnotation;
import com.jerry.annotation.MyMethodAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 *
 * @author jerrychien
 * @create 2016-07-13 14:39
 */
public class MyInceteptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MyInceteptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("--preHandle inteceptor--");
        if (handler instanceof HandlerMethod) {
            logger.info("--preHandle inteceptor HandlerMethod--");
            HandlerMethod method = (HandlerMethod) handler;
            //获取类注解 method.getBean()拿到对应的bean对象,获取类的注解
            MyClassAnnotation myClassAnnotation = method.getBean().getClass().getAnnotation(MyClassAnnotation.class);
            if (myClassAnnotation != null) {
                logger.info("myClassAnnotation uri:{}", myClassAnnotation.uri());
                logger.info("myClassAnnotation desc:{}", myClassAnnotation.desc());
            }
            //获取方法注解,getMethodAnnotation()直接获取方法注解
            MyMethodAnnotation myMethodAnnotation = method.getMethodAnnotation(MyMethodAnnotation.class);
            if (myMethodAnnotation != null) {
                logger.info("myMethodAnnotation uri:{}", myMethodAnnotation.uri());
                logger.info("myMethodAnnotation desc:{}", myMethodAnnotation.desc());
            }
        } else {//非方法,直接获取类注解
            logger.info("--preHandle inteceptor not HandlerMethod--");
            MyClassAnnotation classAnnotation = handler.getClass().getAnnotation(MyClassAnnotation.class);
            if (classAnnotation != null) {
                logger.info("classAnnotation uri:{}", classAnnotation.uri());
                logger.info("classAnnotation desc:{}", classAnnotation.desc());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
