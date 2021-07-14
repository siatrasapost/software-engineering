<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.BasicClasses.student" %>
<%@ page import="java.util.List" %><%--
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
        <span class="logo">Results</span>
        <div class="header-left">
            <a class="home" href="./student_home.jsp">Home</a>
        </div>
        <div class="header-right">
            <a class="logout" href="./logout.jsp">Logout</a>
        </div>
    </div>
    <h3><%= ((student)session.getAttribute("usr_obj")).getUsername()%>, here is your final results and your grade:</h3>
    <br>
    <div>
        <%
            for (int i=0;i<((ArrayList)request.getAttribute("questions")).size();i++){
                out.println((i+1)+". <ins>"+((List<String>)request.getAttribute("questions")).get(i).split(",")[0]+"</ins>");
                out.println("<br>");
                out.println("Your answer was: <span style=\"color:blue\">"+((ArrayList)request.getAttribute("user_choices")).get(i)
                        +"</span> <br>The right answer was: <b>"+((ArrayList)request.getAttribute("right_ones")).get(i)+"</b>");
                out.println("<br><br>");
            }
        %>
    </div>
    <br>
    <p><strong>Your final grade is : <%= request.getAttribute("grade")%> / 10 </strong></p>
    <br>
</body>
</html>
