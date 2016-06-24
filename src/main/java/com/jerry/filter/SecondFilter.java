package com.jerry.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FirstFilter
 *
 * @author jerrychien
 * @create 2016-06-20 22:20
 */
public class SecondFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("--SecondFilter init--");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("--SecondFilter doFilter start--");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
//        System.out.println(req.getRequestURL());
//        System.out.println(req.getContextPath());
        chain.doFilter(req, resp);
        System.out.println("--SecondFilter over--");
    }

    @Override
    public void destroy() {
        System.out.println("--SecondFilter destory--");
    }
}
