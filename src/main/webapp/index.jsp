<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <style>
        body {background-color: #e6e6e6;}
    </style>
    <title>Connect</title>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    response.addHeader("Cache-Control", "post-check=0, pre-check=0");

    //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0);

    //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma", "no-cache");

%>
<form method="post" action="AccountHandling">
    <table align="center"style="margin: 0px auto;">
        <br>
        <br>
        <br>
        <br>
        <h1 style="text-align: center;">Login</h1>
        <tr>
            <td><b>Username:</b></td>
            <td><input type="text" name="username" /></td>
        </tr>
        <tr>
            <td><b>Password:</b></td>
            <td><input type="password" name="password" /></td>
        </tr>
        <td></td>
        <td><input type="submit" value="SignIn" /></td>
        </tr>
        <a href="createuser.jsp"> Εισαγωγη νεου χρηστη</a>
    </table>
</form>
</body>
</html>
