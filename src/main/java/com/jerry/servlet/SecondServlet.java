package com.jerry.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * FirstServlet
 *
 * @author jerrychien
 * @create 2016-06-20 18:57
 */
public class SecondServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(SecondServlet.class);

    private ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.debug("--SecondServlet init--");
        this.config = config;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("-- secondServlet doPost--");
        logger.info("type=" + req.getParameter("type"));
        PrintWriter writer = resp.getWriter();
        writer.write("<html><head>");
        writer.write("<title>");
        writer.write("this is servlet2");
        writer.write("</title>");
        writer.write("</head><body>");
        writer.write("<h1>servelt2 body</h1></body>");
        writer.write("</html>");
        writer.flush();
        writer.close();
        logger.info(config.getServletContext().getServletContextName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("-- secondServlet doGet--");
        doPost(req, resp);
    }

    @Override
    public void destroy() {
        logger.debug("second servlet destory");
    }
}
