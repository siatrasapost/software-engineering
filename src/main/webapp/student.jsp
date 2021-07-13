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
    <title>Select test - Student</title>
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

    if ((String)request.getSession(false).getAttribute("online")==null || !((String)request.getSession(false).getAttribute("acctype")).equals("1")){
        response.sendRedirect("index.jsp");
    }

%>
<div class="header">
    <span class="logo">Select Test</span>
    <div class="header-left">
        <a class="home" href="./student_home.jsp">Home</a>
    </div>
    <div class="header-right">
        <a class="logout" href="./logout.jsp">Logout</a>
    </div>
</div>
<h3>Please select one of the tests your teacher has assigned to you, in order to solve it!</h3>
<form action="test">
    <table class="center">
        <tr>
            <th scope="col">Teacher</th>
            <th scope="col">Questions</th>
            <th scope="col">Difficulty</th>
            <th scope="col">Date Assigned</th>
            <th scope="col">SELECT</th>
        </tr>
        <%
            for (int i=0; i<(int) request.getAttribute("assigned_num"); i++){
                out.println("<tr>");
                out.println("<td>"+((List<String>)request.getAttribute("teachers")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("questions_num")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("difficulty")).get(i)+"</td>");
                out.println("<td>"+((List<Timestamp>)request.getAttribute("dates")).get(i)+"</td>");
                out.println("<th scope=\"row\"><input type=\"radio\" id=\"radio"+i+"\" name=\"selected\" value="+((List<Integer>)request.getAttribute("test_id")).get(i)+" required></td>");
                //out.println("<td><input type=\"hidden\" id=\"test_id"+i+"\" name=\"test_id\" value="+((List<Integer>)request.getAttribute("test_id")).get(i)+" ></td>");
                out.println("</tr>");
            }

            out.println((int)request.getAttribute("assigned_num")==0? "<tr><td colspan=\"5\">NO TESTS ASSIGNED</td></tr>" : "");
        %>
    </table>
    <br>
    <input type="submit" value="Start test" <%= (int)request.getAttribute("assigned_num")==0? "hidden" : ""%>>
</form>
</body>
</html>
