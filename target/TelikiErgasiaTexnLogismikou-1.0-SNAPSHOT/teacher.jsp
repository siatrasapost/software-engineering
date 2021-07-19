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
    <title>Assigned tests - Teacher</title>
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
<div class="header">
    <span class="logo">Assigned Tests</span>
    <div class="header-left">
        <a class="home" href="./teacher_home.jsp">Home</a>
    </div>
    <div class="header-right">
        <a class="logout" href="./logout.jsp">Logout</a>
    </div>
</div>
<h3>Here you can see the tests you have assigned to your students!</h3>
<form method="post" action="Question_Delete">
    <table class="center">
        <tr>
            <th scope="col">Teacher</th>
            <th scope="col">Student's username</th>
            <th scope="col">Questions</th>
            <th scope="col">Difficulty</th>
            <th scope="col">Date Assigned/Solved</th>
            <th scope="col">Grade</th>
            <th scope="col">DELETE</th>
        </tr>
        <%
            for (int i=0; i<(int) request.getAttribute("assigned_num"); i++){
                out.println("<tr>");
                out.println("<td>"+((List<String>)request.getAttribute("teachers")).get(i)+"</td>");
                out.println("<td>"+((List<String>)request.getAttribute("fullnames")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("questions_num")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("difficulty")).get(i)+"</td>");
                out.println("<td>"+((List<Timestamp>)request.getAttribute("dates")).get(i)+"</td>");
                out.println("<td>"+((List<String>)request.getAttribute("grades")).get(i)+"</td>");
                out.println("<th scope=\"row\"><input type=\"radio\" id=\"radio"+i+"\" name=\"selected\" value="+((List<Integer>)request.getAttribute("test_id")).get(i)+" required></td>");
                out.println("</tr>");
            }

            out.println((int)request.getAttribute("assigned_num")==0? "<tr><td colspan=\"7\">NO TESTS ASSIGNED</td></tr>" : "");
        %>
    </table>
    <br>
    <input type="submit" value="Delete Test" <%= (int)request.getAttribute("assigned_num")==0? "hidden" : ""%>>
</form>
</body>
</html>
