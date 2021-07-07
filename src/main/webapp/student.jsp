<%@ page import="com.example.BasicClasses.student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%--
  Created by IntelliJ IDEA.
  User: vagel
  Date: 22/6/2021
  Time: 3:27 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <title>STUDENT</title>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    response.addHeader("Cache-Control", "post-check=0, pre-check=0");

    //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0);

    //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma", "no-cache");

    if ((String)request.getSession(false).getAttribute("online")==null || !((String)request.getSession(false).getAttribute("acctype")).equals("1")){
        response.sendRedirect("index.jsp");
    }

%>
<h1>Welcome, <%= ((student) request.getSession(false).getAttribute("usr_obj")).getUsername() %></h1>
<h4>Please select one of the tests your teacher has assigned to you, in order to solve it!</h4>
<form action="test">
    <table>
        <tr>
            <th>Teacher</th>
            <th>Questions</th>
            <th>Difficulty</th>
            <th>Date Assigned</th>
            <th>SELECT</th>
        </tr>
        <%
            for (int i=0; i<(int) request.getAttribute("assigned_num"); i++){
                out.println("<tr>");
                out.println("<td>"+((List<String>)request.getAttribute("teachers")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("questions_num")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("difficulty")).get(i)+"</td>");
                out.println("<td>"+((List<Timestamp>)request.getAttribute("dates")).get(i)+"</td>");
                out.println("<td><input type=\"radio\" id=\"radio"+i+"\" name=\"selected\" value="+((List<Integer>)request.getAttribute("test_id")).get(i)+" required></td>");
                //out.println("<td><input type=\"hidden\" id=\"test_id"+i+"\" name=\"test_id\" value="+((List<Integer>)request.getAttribute("test_id")).get(i)+" ></td>");
                out.println("</tr>");
            }
        %>
    </table>
    <br>
    <input type="submit" value="Start test">
</form>
<br>
<a href="logout.jsp">Log Out</a>
</body>
</html>
