package com.example.TelikiErgasiaTexnLogismikou;

import com.example.BasicClasses.student;
import com.example.BasicClasses.teacher;
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
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "MyServlet",urlPatterns = {"/MyServlet"})
public class MyServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
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

            if(request.getSession(false).getAttribute("acctype").equals("1")) {
                PreparedStatement ps = con.prepareStatement(((student)request.getSession(false).getAttribute("usr_obj")).getStatement("get_tests"));
                ps.setString(1, ((student)request.getSession(false).getAttribute("usr_obj")).getUsername());
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

                request.setAttribute("assigned_num", assigned_num);
                request.setAttribute("teachers", teacher_arr);
                request.setAttribute("dates", timestamps);
                request.setAttribute("questions_num", questions_num);
                request.setAttribute("difficulty", difficulty);
                request.setAttribute("test_id", test_id);

                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                //Directs caches not to store the page under any circumstance
                response.setDateHeader("Expires", 0);
                //Causes the proxy cache to see the page as "stale"
                response.setHeader("Pragma", "no-cache");

                request.getRequestDispatcher("/student.jsp").include(request, response);

            }
            else {
                PreparedStatement ps = con.prepareStatement(((teacher) request.getSession(false).getAttribute("usr_obj")).getStatement("get_tests"));
                ps.setString(1, ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername());
                ResultSet rs2 = ps.executeQuery();

                List<Integer> test_id = new ArrayList<>();
                List<String> teacher_arr = new ArrayList<>();
                List<Integer> questions_num = new ArrayList<>();
                List<String> students_full = new ArrayList<>();
                int assigned_num = 0;
                List<Integer> difficulty = new ArrayList<>();
                List<Timestamp> timestamps = new ArrayList<>();
                List<String> grades = new ArrayList<>();

                while (rs2.next()) {
                    test_id.add(rs2.getInt("test_id"));
                    teacher_arr.add(rs2.getString("teachersname"));
                    students_full.add(rs2.getString("username"));
                    timestamps.add(rs2.getTimestamp("date"));
                    difficulty.add(rs2.getInt("difficulty"));
                    questions_num.add(rs2.getString("questions").split(",").length);
                    grades.add(rs2.getString("grade"));
                    assigned_num++;
                }

                rs2.close();

                request.setAttribute("assigned_num", assigned_num);
                request.setAttribute("teachers", teacher_arr);
                request.setAttribute("fullnames", students_full);
                request.setAttribute("dates", timestamps);
                request.setAttribute("questions_num", questions_num);
                request.setAttribute("difficulty", difficulty);
                request.setAttribute("test_id", test_id);
                request.setAttribute("grades", grades);

                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                //Directs caches not to store the page under any circumstance
                response.setDateHeader("Expires", 0);
                //Causes the proxy cache to see the page as "stale"
                response.setHeader("Pragma", "no-cache");

                request.getRequestDispatcher("/teacher.jsp").include(request, response);


            }

            con.close();

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

            PreparedStatement ps = con.prepareStatement(((student)request.getSession(false).getAttribute("usr_obj")).getStatement("previous_tests"));
            ps.setString(1, ((student)request.getSession(false).getAttribute("usr_obj")).getUsername());

            ResultSet rs = ps.executeQuery();

            List<String> grade = new ArrayList<>();
            List<String> teacher_arr = new ArrayList<>();
            List<Integer> questions_num = new ArrayList<>();
            int assigned_num = 0;
            List<Integer> difficulty = new ArrayList<>();
            List<Timestamp> timestamps = new ArrayList<>();

            while (rs.next()) {
                teacher_arr.add(rs.getString("teachersname"));
                grade.add(rs.getString("grade"));
                timestamps.add(rs.getTimestamp("date"));
                difficulty.add(rs.getInt("difficulty"));
                questions_num.add(rs.getString("questions").split(",").length);
                assigned_num++;
            }

            rs.close();
            con.close();

            request.setAttribute("assigned_num", assigned_num);
            request.setAttribute("teachers", teacher_arr);
            request.setAttribute("dates", timestamps);
            request.setAttribute("questions_num", questions_num);
            request.setAttribute("difficulty", difficulty);
            request.setAttribute("grades", grade);

            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            //Directs caches not to store the page under any circumstance
            response.setDateHeader("Expires", 0);
            //Causes the proxy cache to see the page as "stale"
            response.setHeader("Pragma", "no-cache");

            request.getRequestDispatcher("/previous.jsp").forward(request, response);

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