<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %><%--
  Created by IntelliJ IDEA.
  User: siatr
  Date: 19/7/2021
  Time: 9:58 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recently solved tests - Teacher</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

  //Directs caches not to store the page under any circumstance
  response.setDateHeader("Expires", 0);

  //Causes the proxy cache to see the page as "stale"
  response.setHeader("Pragma", "no-cache");

  if ((String)request.getSession(false).getAttribute("online")==null || !((String)request.getSession(false).getAttribute("acctype")).equals("2")){
    response.sendRedirect("index.jsp");
  }

%>
<div class="header">
  <span class="logo">Recently Solved Tests</span>
  <div class="header-left">
    <a class="home" href="./teacher_home.jsp">Home</a>
  </div>
  <div class="header-right">
    <a class="logout" href="./logout.jsp">Logout</a>
  </div>
</div>
<h3>Here you can see the tests your students have filled out in the past 24hrs!</h3>
<table class="center">
  <tr>
    <th scope="col">Username</th>
    <th scope="col">Questions</th>
    <th scope="col">Difficulty</th>
    <th scope="col">Date Solved</th>
    <th scope="col">Grade</th>
  </tr>
  <%
    for (int i=0; i<(int) request.getAttribute("recent_num"); i++){
      out.println("<tr>");
      out.println("<td>"+((List<String>)request.getAttribute("recent_usernames")).get(i)+"</td>");
      out.println("<td>"+((List<Integer>)request.getAttribute("recent_questions")).get(i)+"</td>");
      out.println("<td>"+((List<Integer>)request.getAttribute("recent_difficulties")).get(i)+"</td>");
      out.println("<td>"+((List<Timestamp>)request.getAttribute("recent_dates")).get(i)+"</td>");
      out.println("<th scope=\"row\">"+((List<String>)request.getAttribute("recent_grades")).get(i)+"</td>");
      out.println("</tr>");
    }

    out.println((int)request.getAttribute("recent_num")==0? "<tr><td colspan=\"5\">NO TEST HAS BEEN RECENTLY SUBMITTED</td></tr>" : "");
  %>
</table>
</body>
</html>
