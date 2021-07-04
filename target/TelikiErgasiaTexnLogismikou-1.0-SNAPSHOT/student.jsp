<%@ page import="com.example.BasicClasses.student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %><%--
  Created by IntelliJ IDEA.
  User: vagel
  Date: 22/6/2021
  Time: 3:27 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>STUDENT</title>
</head>
<body>
<h1>Welcome, <%= ((student) session.getAttribute("usr_obj")).getUsername() %></h1>
<h4>Please select one of the tests your teacher has assigned to you, in order to solve it!</h4>
<form action="test">
<%--    <input id="regular" name="tests" type="radio" value="regular">--%>
<%--    <label for="regular">Regular Test</label>--%>
<%--    <br>--%>
<%--    <input id="random" name="tests" type="radio" value="random">--%>
<%--    <label for="random">Random Test</label>--%>
<%--    <br>--%>
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
</body>
</html>
