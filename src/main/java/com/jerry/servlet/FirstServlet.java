package com.jerry.servlet;

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

    // private ServletConfig config;

    @Override
    public void init() throws ServletException {
        System.out.println("--firstServlet init--");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("--firstServlet doPost--");
        resp.setContentType("text/html");

        String type = req.getParameter("type");
        type = type == null ? "1" : type;
        if (type.equalsIgnoreCase("1")) {
            System.out.println("--doPost--");
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
            System.out.println(this.getServletContext().getServletContextName());
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
        System.out.println("--firstServlet doGet--");
        doPost(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("first servelt destory");
    }
}
