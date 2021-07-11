<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.example.BasicClasses.teacher" %><%--
  Created by IntelliJ IDEA.
  User: vagel
  Date: 22/6/2021
  Time: 3:28 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TEACHER</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    response.addHeader("Cache-Control", "post-check=0, pre-check=0");

    //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0);

    //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma", "no-cache");

    if ((String)request.getSession(false).getAttribute("online")==null || !((String)request.getSession(false).getAttribute("acctype")).equals("2")){
        response.sendRedirect("index.jsp");
    }

%>
<h1>Welcome, <%= ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername() %></h1>
<h4>Here you can see the tests you have assigned to your students!</h4>
<form action="">
    <table class="center">
        <tr>
            <th scope="col">Teacher</th>
            <th scope="col">Questions</th>
            <th scope="col">Difficulty</th>
            <th scope="col">Date Assigned</th>
            <th scope="col">DELETE</th>
        </tr>
        <%
            for (int i=0; i<(int) request.getAttribute("assigned_num"); i++){
                out.println("<tr>");
                out.println("<td>"+((List<String>)request.getAttribute("teachers")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("questions_num")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("difficulty")).get(i)+"</td>");
                out.println("<td>"+((List<Timestamp>)request.getAttribute("dates")).get(i)+"</td>");
                out.println("<th scope=\"row\"><input type=\"radio\" id=\"radio"+i+"\" name=\"selected\" value="+((List<Integer>)request.getAttribute("test_id")).get(i)+" required></td>");
                out.println("</tr>");
            }

            out.println((int)request.getAttribute("assigned_num")==0? "<tr><td colspan=\"5\">NO TESTS ASSIGNED</td></tr>" : "");
        %>
    </table>
    <br>
    <input type="button" value="Delete Test" <%= (int)request.getAttribute("assigned_num")==0? "hidden" : ""%>>
</form>
<br>
<a href="logout.jsp">Log Out</a>

</body>
</html>
