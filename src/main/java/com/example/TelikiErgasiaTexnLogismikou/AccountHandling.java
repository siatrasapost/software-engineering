package com.example.TelikiErgasiaTexnLogismikou;

import com.example.BasicClasses.student;

import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "AccountHandling", value = "/AccountHandling")
public class AccountHandling extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetUsername = null;
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma","no-cache");
        request.getSession(false).removeAttribute("online");
        request.getSession(false).removeAttribute("usr_obj");
        request.getSession(false).invalidate();
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Session
        //(NOT WORKING PROPERLY, IT HAS TO INVALIDATE SESSION RIGHT, SO NO SENSITIVE INFO CAN BE ACCESSED AFTER LOGOUT!!!)
        session = request.getSession(true);
        session.setAttribute("online", "true");
        GetUsername = request.getParameter("username");
        GetPassword = request.getParameter("password");
        password1 = Encryption.getHashMD5(GetPassword,"alevrialati");


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
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='" + GetUsername + "' AND password='" + password1 + "'");

            String name = "";
            String surname = "";
            String acctype = "0";

            while (rs.next()) {
                name = rs.getString("firstname");
                surname = rs.getString("lastname");
                acctype = rs.getString("typeacc");
                session.setAttribute("acctype", acctype);
            }

            rs.close();
            con.close();

            if (acctype.equals("0")){
                response.setContentType("text/html; charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                request.setCharacterEncoding("UTF-8");
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<style>");
                out.println("<body {background-color: #e6e6e6;}>");
                out.println("</style>");
                out.println("<script type=text/javascript>");
                out.println("alert(\"Incorrect username or surname\");");
                out.println("location.replace(\"index.jsp\")");
                out.println("</script>");
                out.println("</body>");
                out.println("</html>");
            }
            else if (acctype.equals("1")){
                user1 = new student();
                user1.login(GetUsername, password1, name, surname, "student");
                //user1.setUniqueID(session.getId());

                session.setAttribute("usr_obj", user1);

                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                //Directs caches not to store the page under any circumstance
                response.setDateHeader("Expires", 0);
                //Causes the proxy cache to see the page as "stale"
                response.setHeader("Pragma", "no-cache");

                request.getRequestDispatcher("/blank.html").include(request, response);
            }
            else{
                request.getRequestDispatcher("/teacher.jsp").forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        catch(Exception e) {
            out.println("Database connection problem\n" + e.toString());
        }
        out.println("</body>");
        out.println("</html>");
    }
}
