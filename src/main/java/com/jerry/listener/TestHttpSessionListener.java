package com.jerry.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HttpSessionSLister
 *
 * 此HttpSession可以用来统计在线用户数量
 * @author jerrychien
 * @create 2016-06-23 18:18
 */
public class TestHttpSessionListener implements HttpSessionListener {

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("[--------------------------------------sessionCreated]");
        HttpSession session = se.getSession();
        long dd = session.getLastAccessedTime();
        session.setMaxInactiveInterval(10);
        System.out.println("MaxInactiveInterval:" + session.getMaxInactiveInterval());
        System.out.println("accessTime:" + dd);
        System.out.println("currentTime:" + new Date());
        System.out.println("id:" + session.getId());
        onlineCount.getAndIncrement();
        this.updateServletContextOnlineCount(session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("[sessionDestroyed---------------------------------------]");
        HttpSession session = se.getSession();
        long ff = session.getLastAccessedTime();
        System.out.println("accessTime:" + ff);
        System.out.println("currentTime:" + new Date());
        System.out.println("id:" + session.getId());
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
