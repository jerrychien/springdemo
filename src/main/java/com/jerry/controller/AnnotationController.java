package com.jerry.controller;

import com.jerry.annotation.MyClassAnnotation;
import com.jerry.annotation.MyMethodAnnotation;
import com.jerry.model.ResponseResult;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 带有注解的controller
 *
 * @author jerrychien
 * @create 2016-07-13 15:29
 */
@Controller
@MyClassAnnotation(uri = "myClassAnnotationURI", desc = "myClassAnnotationDesc")
public class AnnotationController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @RequestMapping("/getAnno")
    @MyMethodAnnotation(uri = "getAnno uri", desc = "getAnno desc")
    @ResponseBody
    public ResponseResult<String> getAnno() {
        return new ResponseResult(0, "SUCC", "SUCC");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
