<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %><%--
  Created by IntelliJ IDEA.
  User: siatr
  Date: 13/7/2021
  Time: 3:29 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Previous tests - Student</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0);

    //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma", "no-cache");

    if ((String)request.getSession(false).getAttribute("online")==null || !((String)request.getSession(false).getAttribute("acctype")).equals("1")){
        response.sendRedirect("index.jsp");
    }

%>
<div class="header">
    <span class="logo">Previous Tests</span>
    <div class="header-left">
        <a class="home" href="./student_home.jsp">Home</a>
    </div>
    <div class="header-right">
        <a class="logout" href="./logout.jsp">Logout</a>
    </div>
</div>
<%--<h1>Previous Tests</h1>--%>
<h3>Here you can see the previous tests you have filled out!</h3>
<table class="center">
    <tr>
        <th scope="col">Teacher</th>
        <th scope="col">Questions</th>
        <th scope="col">Difficulty</th>
        <th scope="col">Date Solved</th>
        <th scope="col">Grade</th>
    </tr>
    <%
        for (int i=0; i<(int) request.getAttribute("assigned_num"); i++){
            out.println("<tr>");
            out.println("<td>"+((List<String>)request.getAttribute("teachers")).get(i)+"</td>");
            out.println("<td>"+((List<Integer>)request.getAttribute("questions_num")).get(i)+"</td>");
            out.println("<td>"+((List<Integer>)request.getAttribute("difficulty")).get(i)+"</td>");
            out.println("<td>"+((List<Timestamp>)request.getAttribute("dates")).get(i)+"</td>");
            out.println("<th scope=\"row\">"+((List<String>)request.getAttribute("grades")).get(i)+"</td>");
            out.println("</tr>");
        }

        out.println((int)request.getAttribute("assigned_num")==0? "<tr><td colspan=\"5\">NO TESTS SUBMITTED</td></tr>" : "");
    %>
</table>
</body>
</html>
