package com.jerry.servlet;

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

    private ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("--SecondServlet init--");
        this.config = config;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("-- secondServlet doPost--");
        System.out.println("type=" + req.getParameter("type"));
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
        System.out.println(config.getServletContext().getServletContextName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("-- secondServlet doGet--");
        doPost(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("second servlet destory");
    }
}
