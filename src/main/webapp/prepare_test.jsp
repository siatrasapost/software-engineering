<%--
  Created by IntelliJ IDEA.
  User: siatr
  Date: 12/7/2021
  Time: 4:44 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Preparing test assignment - Teacher</title>
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
        <span class="logo">Preparing Test Assignment</span>
        <div class="header-left">
            <a class="home" href="./teacher_home.jsp">Home</a>
        </div>
        <div class="header-right">
            <a class="logout" href="./logout.jsp">Logout</a>
        </div>
    </div>
    <h3>Please select the required features in order to assign a test to your students.</h3>
    <form method="get" action="AssignTests">
        <br>
        <br>
        <b>Question difficulty: </b>
        <select name="questdif">
            <option value="1" selected>1</option>
            <option>2</option>
            <option>3</option>
        </select>
        <br><br>
        <b>Type of question: </b>
        <label for="multichoice">Multiple choice</label>
        <input type="radio" name="yesno" id="multichoice" value="multichoice">
        <label for="filltheblank">Fill the blank</label>
        <input type="radio" name="yesno" id="filltheblank" value="filltheblank">
        <label for="trueorfalse">True or false</label>
        <input type="radio" name="yesno" id="trueorfalse" value="trueorfalse" required>
        <br><br>
        <b>Amount of exercises: </b>
        <input type="number" name="amount" min="1" max="10" required>
        <br><br>
        <b>Age: </b>
        <input type="number" name="age" min="10" max="100" required>
        <br><br>
        <input type="submit" value="Continue">
    </form>
</body>
</html>
