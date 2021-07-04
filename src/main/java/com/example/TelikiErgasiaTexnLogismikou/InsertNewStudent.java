package com.example.TelikiErgasiaTexnLogismikou;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "InsertNewStudent",urlPatterns = "/InsertNewStudent")
public class InsertNewStudent extends HttpServlet {
    private DataSource datasource = null;

    public void init() throws ServletException{
        try {

            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
        } catch(Exception e) {
            throw new ServletException(e.toString());
        }

    }
    public class  doPut {
        public Boolean username(String username){
            Boolean temp=false;
            try {
                Connection con = datasource.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT username FROM users where username='"+username+"'");
                while (rs.next()) {
                    temp = true;
                }
                rs.close();
                con.close();
            } catch (Exception e) {
            }
            return temp;
        }
    }
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
        String username = request.getParameter("username");
        doPut newseller = new doPut();
        Boolean temp = newseller.username(username);
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String typeacc = request.getParameter("typeacc");
        Boolean temp1=false;
        if(typeacc.equals("2")) {
            String code = request.getParameter("code");
            if (!code.equals("123456")) {
                temp1=true;
            }
        }
        if (temp || temp1) {
            if(temp) {
                out.println("<script type=text/javascript>");
                out.println("alert(\"Username already exists\");");
                out.println("location.replace(\"createuser.jsp\")");
                out.println("</script>");
            }
            else if(temp1){
                out.println("<script type=text/javascript>");
                out.println("alert(\"6-digit code is wrong.\");");
                out.println("location.replace(\"createuser.jsp\")");
                out.println("</script>");
            }
            } else {
            try {
                Connection con = datasource.getConnection();
                PreparedStatement st = con.prepareStatement("INSERT INTO users (username, password,firstname, lastname, typeacc) VALUES (?, ?, ?, ?,?)");

                st.setString(1, username);
                st.setString(2, Encryption.getHashMD5(password, "alevrialati"));
                st.setString(3, firstname);
                st.setString(4, lastname);
                st.setString(5, typeacc);
                st.executeUpdate();
                st.close();
                con.close();

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        out.println("<script type=text/javascript>");
        out.println("alert(\"Seller successfully created\");");
        out.println("location.replace(\"student.jsp\")");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
    }
}

