package com.jerry.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HttpSessionSLister
 * <p/>
 * 此HttpSession可以用来统计在线用户数量
 *
 * @author jerrychien
 * @create 2016-06-23 18:18
 */
public class TestHttpSessionListener implements HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(TestHttpSessionListener.class);

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("[--------------------------------------sessionCreated]");
        HttpSession session = se.getSession();
        long dd = session.getLastAccessedTime();
        session.setMaxInactiveInterval(10);
        logger.info("MaxInactiveInterval:" + session.getMaxInactiveInterval());
        logger.info("accessTime:" + dd);
        logger.info("currentTime:" + new Date());
        logger.info("id:" + session.getId());
        onlineCount.getAndIncrement();
        this.updateServletContextOnlineCount(session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("[sessionDestroyed---------------------------------------]");
        HttpSession session = se.getSession();
        long ff = session.getLastAccessedTime();
        logger.info("accessTime:" + ff);
        logger.info("currentTime:" + new Date());
        logger.info("id:" + session.getId());
        onlineCount.getAndDecrement();
        this.updateServletContextOnlineCount(session);
    }

    /**
     * 设置全局参数ONLINE
     *
     * @param session
     */
    private void updateServletContextOnlineCount(HttpSession session) {
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("ONLINE", onlineCount.get());
    }
}
