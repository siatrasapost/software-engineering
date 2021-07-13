package com.example.TelikiErgasiaTexnLogismikou;

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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@WebServlet(name = "AssignTests", value = "/AssignTests")
public class AssignTests extends HttpServlet {
    private DataSource datasource = null;

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
            PreparedStatement st = con.prepareStatement((((teacher)request.getSession(false).getAttribute("usr_obj")).getStatement("get_students")));
            st.setInt(1, Integer.parseInt(request.getParameter("age")));
            ResultSet rs = st.executeQuery();

            List<String> usernames = new ArrayList<>();
            List<String> firstnames = new ArrayList<>();
            List<String> lastnames = new ArrayList<>();
            List<Integer> ages = new ArrayList<>();
            int eligible = 0;

            while (rs.next()){
                usernames.add(rs.getString("username"));
                firstnames.add(rs.getString("firstname"));
                lastnames.add(rs.getString("lastname"));
                ages.add(rs.getInt("age"));
                eligible++;
            }

            rs.close();
            st.close();
            con.close();

            request.setAttribute("usernames", usernames);
            request.setAttribute("firstnames", firstnames);
            request.setAttribute("lastnames", lastnames);
            request.setAttribute("ages", ages);
            request.setAttribute("eligible", eligible);

            request.getRequestDispatcher("/AssignTests.jsp").forward(request, response);

        } catch (SQLException e){
            out.println("Database connection problem\n");
            out.println(e.toString());
        }
        catch(Exception e) {
            out.println("<script>");
            out.println("alert('You have to login in order to access this page! ');");
            out.println("location.replace('./index.jsp');");
            out.println("</script>");
        }


        out.println("</body>");
        out.println("</html>");
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
            Connection con1 = datasource.getConnection();

            String[] values = request.getParameterValues("selected");
            List<String> selected = new ArrayList<>(Arrays.asList(values));

            PreparedStatement st = null;
            for (int i = 0; i < selected.size(); i++) {
                PreparedStatement st1 = con1.prepareStatement("SELECT question_id FROM questions WHERE difficulty = ? AND type = ?");
                st1.setString(1, request.getParameter("difficulty"));
                switch (request.getParameter("quest_type")){
                    case "multichoice":
                        st1.setInt(2, 1);
                    case "filltheblank":
                        st1.setInt(2, 2);
                    case "trueorfalse":
                        st1.setInt(2, 3);
                }
                ResultSet rs = st1.executeQuery();

                List<Integer> ids = new ArrayList<>();
                while (rs.next()){
                    ids.add(rs.getInt("question_id"));
                }

                rs.close();
                st1.close();

                Random random = new Random();

                List<Integer> questions = new ArrayList<>();
                for (int j = 0; j < Integer.parseInt(request.getParameter("quest_amount")); j++) {
                    if (!ids.isEmpty()) {
                        int randomIndex = random.nextInt(ids.size());
                        questions.add(ids.get(randomIndex));
                        ids.remove(randomIndex);
                    }
                }



                st = con.prepareStatement((((teacher)request.getSession(false).getAttribute("usr_obj"))
                        .getStatement("assign_test")));
                st.setString(1, selected.get(i));
                st.setString(2, "");
                st.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                st.setString(4, ((teacher)request.getSession(false).getAttribute("usr_obj")).getUsername());
                st.setString(5, Arrays.toString(questions.toArray()).replace("[", "").replace("]", "").replace(" ", ""));
                st.setInt(6, Integer.parseInt(request.getParameter("difficulty")));

                st.executeUpdate();

                //System.gc();
            }

            st.close();
            con.close();
            con1.close();

            con1.close();
            request.getRequestDispatcher("/teacher_home.jsp").forward(request, response);

        } catch (SQLException e){
            out.println("Database connection problem\n");
            out.println(e.toString());
        }
        catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            out.println("<script>");
            out.println("alert('You have to login in order to access this page! ');");
            out.println("location.replace('./index.jsp');");
            out.println("</script>");
        }


        out.println("</body>");
        out.println("</html>");
    }


    //What if επιλεχθουν 10 εργασιες τυπου 1 αλλα υπαρχουν μολις 5 στην βαση
    //Πρεπει να πεταει καποιο μηνυμα ωστε να μην συνεχιζει ο καθηγητης να φτιαχνει το τεστ!!!!!!
}
