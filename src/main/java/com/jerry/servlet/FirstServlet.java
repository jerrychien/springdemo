package com.jerry.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
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
public class FirstServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(FirstServlet.class);

    // private ServletConfig config;

    @Override
    public void init() throws ServletException {
        logger.debug("--firstServlet init--");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("--firstServlet doPost--");
        resp.setContentType("text/html");

        String type = req.getParameter("type");
        type = type == null ? "1" : type;
        if (type.equalsIgnoreCase("1")) {
            logger.info("--doPost--");
            PrintWriter writer = resp.getWriter();
            writer.write("<html><head>");
            writer.write("<title>");
            writer.write("this is servlet");
            writer.write("</title>");
            writer.write("</head><body>");
            writer.write("<h1>servelt body</h1></body>");
            writer.write("</html>");
            writer.flush();
            writer.close();
            logger.info(this.getServletContext().getServletContextName());
        } else if (type.equalsIgnoreCase("2")) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/secondServlet");
            dispatcher.forward(req, resp);
        } else if (type.equalsIgnoreCase("3")) {
            resp.sendRedirect("/index");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("--firstServlet doGet--");
        doPost(req, resp);
    }

    @Override
    public void destroy() {
        logger.debug("first servelt destory");
    }
}
