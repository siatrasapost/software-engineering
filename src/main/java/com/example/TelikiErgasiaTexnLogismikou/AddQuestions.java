package com.example.TelikiErgasiaTexnLogismikou;

import com.example.BasicClasses.student;

import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddQuestions", urlPatterns = {"/AddQuestions"})
public class AddQuestions extends HttpServlet {
    private DataSource datasource = null;
    public static HttpSession session;
    private student user1;
    String GetUsername = null;
    String GetPassword = null;
    String password1 = null;

    @Override
    public void init() throws ServletException {
        try {

            InitialContext ctx = new InitialContext();
            datasource = (DataSource) ctx.lookup("java:comp/env/jdbc/LiveDataSource");
        } catch (Exception e) {
            throw new ServletException(e.toString());
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int j=0;
        PrintWriter out = response.getWriter();

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<style>");
        out.println("<body {background-color: #e6e6e6;}>");
        out.println("</style>");



        out.println("</body>");
        out.println("</html>");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
