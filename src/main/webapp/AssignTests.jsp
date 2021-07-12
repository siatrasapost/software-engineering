<%@ page import="com.example.BasicClasses.teacher" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: vagel
  Date: 12/7/2021
  Time: 8:46 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Assign tests - Teacher</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <meta charset="UTF-8">
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
<h1>Assign Tests</h1>
<h3>Here you can assign tests to students!</h3>
<form method="post" action="AssignTests">
    <table class="center">
        <tr>
            <th scope="col">Username</th>
            <th scope="col">Name</th>
            <th scope="col">Surname</th>
            <th scope="col">Age</th>
            <th scope="col">SELECT</th>
        </tr>
        <%
            for (int i=0; i<(int) request.getAttribute("eligible"); i++){
                out.println("<tr>");
                out.println("<td>"+((List<String>)request.getAttribute("usernames")).get(i)+"</td>");
                out.println("<td>"+((List<String>)request.getAttribute("firstnames")).get(i)+"</td>");
                out.println("<td>"+((List<String>)request.getAttribute("lastnames")).get(i)+"</td>");
                out.println("<td>"+((List<Integer>)request.getAttribute("ages")).get(i)+"</td>");
                out.println("<th scope=\"row\"><input type=\"checkbox\" id=\"checkbox"+i+"\" name=\"selected\" value="+((List<String>)request.getAttribute("usernames")).get(i)+"></td>");
                out.println("</tr>");
            }

            out.println((int)request.getAttribute("eligible")==0? "<tr><td colspan=\"5\">NO STUDENTS FOUND</td></tr>" : "");
        %>
    </table>
    <input type="hidden" name="quest_type" value="<%= request.getParameter("yesno")%>">
    <input type="hidden" name="difficulty" value="<%= request.getParameter("questdif")%>">
    <input type="hidden" name="quest_amount" value="<%= request.getParameter("amount")%>">
    <input type="hidden" name="eligible_num" value="<%= request.getParameter("eligible")%>">
    <input type="submit" value="Assign Test">
</form>
</body>
</html>
