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

@WebServlet(name = "test",urlPatterns = {"/test"})
public class test extends HttpServlet {

    private DataSource datasource = null;
    List<String> quest = new ArrayList();
    List<String> ans = new ArrayList();

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
        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        int j=0;
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<title>Test</title>");
        out.println("<style>");
        out.println("<body {background-color: #e6e6e6;}>");
        out.println("</style>");

        try {
            Connection con = datasource.getConnection();
            Statement stmt = con.createStatement();

//            String test_id = request.getParameter("test_id");
//            System.out.println(test_id);
            ResultSet rs = stmt.executeQuery("SELECT questions FROM test_history WHERE test_id = "+ request.getParameter("selected"));   //SELECT * FROM questions WHERE questions = (SELECT questions FROM test_history WHERE grade='';)
                                                                                //these has to be equal() since both of them are varchars.

            String question;
            String answer;
            String[] question_id = new String[0];

            quest.clear();
            ans.clear();

            while (rs.next()){
                question_id = rs.getString("questions").split(",");
            }

            for (int i=0; i<question_id.length; i++){
                rs = stmt.executeQuery("SELECT * FROM questions WHERE question_id = "+question_id[i]);

                while (rs.next()) {
                    question = rs.getString("question");
                    answer = rs.getString("answer");
                    quest.add(question);
                    ans.add(answer);
                }
            }


            //request.setAttribute("test_type", request.getParameter("tests"));
            request.setAttribute("questions", quest);
            request.setAttribute("answers", ans);
            request.setAttribute("test_id", request.getParameter("selected"));

            request.getRequestDispatcher("/test.jsp").forward(request, response);
            rs.close();
            stmt.close();

            out.println("\t\t<input type=\"submit\" value=\"Start test\" />\t");
        }
        catch(Exception e) {
            out.println("Database connection problem\n");
            out.println(e.toString());
        }
        out.println("</body>");
        out.println("</html>");

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        List<String> useranswer = new ArrayList<>();
        int wrongs = 0;
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");

        for (int i=0;i<quest.size();i++){
            useranswer.add(request.getParameter("textbox"+i));
            //System.out.println(useranswer);
        }

        for (int j=0;j<ans.size();j++){
            if(!ans.get(j).equalsIgnoreCase(useranswer.get(j))){
                //System.out.println(ans.get(j) + " " + useranswer.get(j));
//                out.println("<script>");
//                out.println("alert(\"H erwthsh <"+quest.get(j)+"> einai lan8asmenh\");");
//                out.println("</script>");
                wrongs++;
            }
        }

        student st1 = ((student)request.getSession(false).getAttribute("usr_obj"));
        double rights = quest.size()-wrongs;
        double grade = Math.round( ( ( rights / (double) quest.size()) / 0.1 ) * 100.0 ) / 100.0;

        try {
            Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareStatement(st1.getStatement("submit_answers"));
            ps.setString(1, Double.toString(grade));
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            System.out.println(request.getAttribute("id_test"));
            ps.setInt(3, Integer.parseInt(request.getParameter("id_test")));
            ps.executeUpdate();
            ps.close();
            con.close();

            request.setAttribute("user_choices", useranswer);
            request.setAttribute("right_ones", ans);
            request.setAttribute("questions", quest);
            request.setAttribute("grade", grade);

            request.getRequestDispatcher("/results.jsp").forward(request, response);

        }
        catch (SQLException e){
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
}