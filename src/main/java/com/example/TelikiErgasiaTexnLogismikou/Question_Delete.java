package com.example.TelikiErgasiaTexnLogismikou;

import com.example.BasicClasses.teacher;

import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "Question_Delete", value = "/Question_Delete")
public class Question_Delete extends HttpServlet {
    private DataSource datasource = null;
    public static HttpSession session;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
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
        try {
            Connection con = datasource.getConnection();
            PreparedStatement st = con.prepareStatement(((teacher) request.getSession(false).getAttribute("usr_obj")).getStatement("delete_test"));
            st.setInt(1, Integer.parseInt(request.getParameter("selected")));
            st.executeUpdate();
            st.close();
            con.close();

            request.getRequestDispatcher("/MyServlet").include(request,response);
            out.println("<script>");
            out.println("alert('Test has been deleted');");
            out.println("</script>");
        } catch (SQLException e){
            out.println("Database connection problem\n");
            out.println(e.toString());
        }
    }
}
