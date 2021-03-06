package com.jerry.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * InitListener
 * <p/>
 * Implementations of this interface recieve notifications about changes
 * to the servlet context of the web application they are part of.
 * <p/>
 * 当servletContext有变动时会收到通知
 *
 * @author jerrychien
 * @create 2016-06-23 11:43
 */
public class TestServletContextListenser implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(TestServletContextListenser.class);

    private static final String COUNT_FILE = "/Users/jerrychien/Documents/count.log";

    private static final String ATTR_NAME = "totalCount";

    public void contextInitialized(ServletContextEvent sce) {
        logger.info("----TestServletContextListenser contextInitialized----");
        ServletContext servletContext = sce.getServletContext();
        int count = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(COUNT_FILE)));
            count = Integer.valueOf(reader.readLine());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("get count: " + count);
        servletContext.setAttribute(ATTR_NAME, count);
        try {
            int bb = (Integer) servletContext.getAttribute(ATTR_NAME) + 1;
            logger.info("what the xxx: " + bb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("---TestServletContextListenser contextDestroyed");
        ServletContext servletContext = sce.getServletContext();
        int count = (Integer) servletContext.getAttribute(ATTR_NAME) + 1;
        logger.info("save count:" + count);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(COUNT_FILE)));
            writer.write(String.valueOf(count));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
