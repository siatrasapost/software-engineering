package com.example.TelikiErgasiaTexnLogismikou;

import com.example.BasicClasses.student;
import com.example.BasicClasses.teacher;

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
        try {
            Connection con = datasource.getConnection();
            PreparedStatement st = con.prepareStatement("INSERT INTO questions(question,answer,difficulty,teachersname,type) VALUES (?, ?, ?, ?,?)");
            System.out.println(request.getParameter("myField"));
            if(request.getParameter("myField").equals("multichoice")) {
                st.setString(1, request.getParameter("question1")+","+request.getParameter("choice1")+","+request.getParameter("choice_correct")+","+request.getParameter("choice2"));
                st.setString(2, request.getParameter("choice_correct"));
                st.setString(3, request.getParameter("questdif"));
                st.setString(4, ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername());
                st.setInt(5,1);
            }
            else if(request.getParameter("myField").equals("filltheblank")) {
                st.setString(1, request.getParameter("question2"));
                st.setString(2, request.getParameter("anwser"));
                st.setString(3, request.getParameter("questdif"));
                st.setString(4, ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername());
                st.setInt(5,2);
            }
            else if(request.getParameter("myField").equals("trueorfalse")) {
                System.out.println(request.getParameter("question3")+"  "+ request.getParameter("first_item")+"  "+request.getParameter("questdif") );
                st.setString(1, request.getParameter("question3"));
                st.setString(2, request.getParameter("first_item"));
                st.setString(3, request.getParameter("questdif"));
                st.setString(4, ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername());
                st.setInt(5,3);
            }

            st.executeUpdate();
            st.close();
            con.close();
            out.println("<script>");
            out.println("alert('Question has been added succesfully!');");
            out.println("location.replace('./AddQuestion.jsp');");
            out.println("</script>");
            j=0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }


        out.println("</body>");
        out.println("</html>");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
