<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.BasicClasses.student" %><%--
  Created by IntelliJ IDEA.
  User: siatr
  Date: 3/7/2021
  Time: 1:20 π.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Results</h1>
    <h4><%= ((student)session.getAttribute("usr_obj")).getUsername()%>, here is your final results and your grade:</h4>
    <br>
    <div>
        <%
            for (int i=0;i<((ArrayList)request.getAttribute("questions")).size();i++){
                out.println(((ArrayList)request.getAttribute("questions")).get(i));
                out.println("<br>");
                out.println("Your answer was: "+((ArrayList)request.getAttribute("user_choices")).get(i)
                        +" <br>The right answer was: "+((ArrayList)request.getAttribute("right_ones")).get(i));
                out.println("<br><br>");
            }
        %>
    </div>
    <br>
    <p><strong>Your final grade is : <%= request.getAttribute("grade")%> / 10 </strong></p>
    <br>
    <a href="index.jsp">Home Page</a>
</body>
</html>
