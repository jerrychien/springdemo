package com.jerry.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(SecondFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("--SecondFilter init--");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("--SecondFilter doFilter start--");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
//        logger.info(req.getRequestURL());
//        logger.info(req.getContextPath());
        chain.doFilter(req, resp);
        logger.info("--SecondFilter over--");
    }

    @Override
    public void destroy() {
        logger.debug("--SecondFilter destory--");
    }
}
