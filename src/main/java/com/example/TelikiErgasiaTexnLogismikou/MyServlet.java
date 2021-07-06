package com.example.TelikiErgasiaTexnLogismikou;

import com.example.BasicClasses.student;
import com.example.BasicClasses.user;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MyServlet",urlPatterns = {"/MyServlet"})
public class MyServlet extends HttpServlet {
    private DataSource datasource = null;
    int i=0;
    public static HttpSession session;
    private student user1;

    public void init() throws ServletException{
        try {

            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
        } catch(Exception e) {
            throw new ServletException(e.toString());
        }

    }
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int j=0;
        String GetUsername = "";
        String GetPassword = "";
        String password1 = "";

        //Session
        //(NOT WORKING PROPERLY, IT HAS TO INVALIDATE SESSION RIGHT, SO NO SENSITIVE INFO CAN BE ACCESSED AFTER LOGOUT!!!)
        session = request.getSession(true);
        synchronized (session){
            if (session.getAttribute("online") == null){
                session.setAttribute("online", "true");
                GetUsername = request.getParameter("username");
                GetPassword = request.getParameter("password");
                password1 = Encryption.getHashMD5(GetPassword,"alevrialati");
                System.out.println(session.getAttribute("online"));
            }
            else {
                GetUsername = ((student)session.getAttribute("usr_obj")).getUsername();
                GetPassword = ((student)session.getAttribute("usr_obj")).getPassword();
                password1 = Encryption.getHashMD5(GetPassword,"alevrialati");
                System.out.println(GetUsername+ " " + GetPassword);
            }
        }

        PrintWriter out = response.getWriter();
        try {
            Connection con = datasource.getConnection();
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='"+GetUsername+"' AND password='"+password1+"'" );

            String name="";
            String surname="";
            String acctype="";

            while(rs.next()) {
                j++;
                name = rs.getString("firstname");
                surname = rs.getString("lastname");
                acctype = rs.getString("typeacc");
                session.setAttribute("acctype",acctype);
            }
            rs.close();
            //con.close();

            if(j==0){response.setContentType("text/html; charset=UTF-8");
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
                out.println("</html>");}

            if(acctype.equals("1")) {
                //response.sendRedirect("student.jsp");
                user1 = new student();
                user1.login(GetUsername, GetPassword, name, surname, "student");
                user1.setUniqueID(session.getId());

                session.setAttribute("usr_obj", user1);

                //response.sendRedirect("student.jsp");

                PreparedStatement ps = con.prepareStatement(user1.getStatement("get_tests"));
                ps.setString(1, GetUsername);
                ResultSet rs1 = ps.executeQuery();

                List<Integer> test_id = new ArrayList<>();
                List<String> teacher_arr = new ArrayList<>();
                List<Integer> questions_num = new ArrayList<>();
                int assigned_num=0;
                List<Integer> difficulty = new ArrayList<>();
                List<Timestamp> timestamps = new ArrayList<>();

                while (rs1.next()){
                    test_id.add(rs1.getInt("test_id"));
                    teacher_arr.add(rs1.getString("teachersname"));
                    timestamps.add(rs1.getTimestamp("date"));
                    difficulty.add(rs1.getInt("difficulty"));
                    questions_num.add(rs1.getString("questions").split(",").length);
                    assigned_num++;
                }

                rs1.close();
                con.close();

                request.setAttribute("assigned_num", assigned_num);
                request.setAttribute("teachers", teacher_arr);
                request.setAttribute("dates", timestamps);
                request.setAttribute("questions_num", questions_num);
                request.setAttribute("difficulty", difficulty);
                request.setAttribute("test_id", test_id);

                request.getRequestDispatcher("/student.jsp").forward(request, response);

            }
            else
                response.sendRedirect("teacher.jsp");

        } catch(Exception e) {
            out.println("Database connection problem\n" + e.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession(false).invalidate();

        resp.sendRedirect("index.jsp");
    }
}