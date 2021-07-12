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
            PreparedStatement st = con.prepareStatement(((teacher) request.getSession(false).getAttribute("usr_obj")).getStatement("create_exercise"));
            System.out.println(request.getParameter("yesno"));
            if(request.getParameter("yesno").equals("multichoice")) {
                st.setString(1, request.getParameter("question1")+","+request.getParameter("choice1")+","+request.getParameter("choice_correct")+","+request.getParameter("choice2"));
                st.setString(2, request.getParameter("choice_correct"));
                st.setString(3, request.getParameter("questdif"));
                st.setString(4, ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername());
                st.setInt(5,1);
            }
            else if(request.getParameter("yesno").equals("filltheblank")) {
                st.setString(1, request.getParameter("question2"));
                st.setString(2, request.getParameter("anwser"));
                st.setString(3, request.getParameter("questdif"));
                st.setString(4, ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername());
                st.setInt(5,2);
            }
            else if(request.getParameter("yesno").equals("trueorfalse")) {
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
        } catch (SQLException e){
            out.println("Database connection problem\n");
            out.println(e.toString());
        }
        catch(Exception e) {
            out.println("<script>");
            out.println("alert('You have to login in order to access this page!');");
            out.println("location.replace('./index.jsp');");
            out.println("</script>");
        }


        out.println("</body>");
        out.println("</html>");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
