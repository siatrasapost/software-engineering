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
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "GetRecentResults", value = "/GetRecentResults")
public class GetRecentResults extends HttpServlet {
    private DataSource datasource = null;

    public void init() throws ServletException{
        try {
            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
        } catch(Exception e) {
            throw new ServletException(e.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

            PreparedStatement tests = con.prepareStatement(((teacher) request.getSession(false)
                    .getAttribute("usr_obj")).getStatement("get_recent_tests"));
            tests.setString(1, ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername());
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -1);
            tests.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            tests.setTimestamp(3, new Timestamp(c.getTimeInMillis()));

            ResultSet rs3 = tests.executeQuery();

            List<Integer> questions_num_rec = new ArrayList<>();
            List<String> usernames_rec = new ArrayList<>();
            int recent_num = 0;
            List<Integer> difficulty_rec = new ArrayList<>();
            List<Timestamp> timestamps_rec = new ArrayList<>();
            List<String> grades_rec = new ArrayList<>();
            while (rs3.next()){
                questions_num_rec.add(rs3.getString("questions").split(",").length);
                usernames_rec.add(rs3.getString("username"));
                difficulty_rec.add(rs3.getInt("difficulty"));
                timestamps_rec.add(rs3.getTimestamp("date"));
                grades_rec.add(rs3.getString("grade"));
                recent_num++;
            }

            rs3.close();
            con.close();

            request.setAttribute("recent_questions", questions_num_rec);
            request.setAttribute("recent_usernames", usernames_rec);
            request.setAttribute("recent_difficulties", difficulty_rec);
            request.setAttribute("recent_dates", timestamps_rec);
            request.setAttribute("recent_grades", grades_rec);
            request.setAttribute("recent_num", recent_num);

            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            //Directs caches not to store the page under any circumstance
            response.setDateHeader("Expires", 0);
            //Causes the proxy cache to see the page as "stale"
            response.setHeader("Pragma", "no-cache");

            request.getRequestDispatcher("/recent_solved.jsp").forward(request, response);

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
    }
}
