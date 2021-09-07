<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <title>Login</title>
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

%>
<h1 style="text-align: center;">Login</h1>
<form method="post" action="AccountHandling">
    <table align="center"style="margin: 0px auto;">
        <tr>
            <td><b>Username:</b></td>
            <td><input type="text" name="username" /></td>
        </tr>
        <tr>
            <td><b>Password:</b></td>
            <td><input type="password" name="password" /></td>
        </tr>
    </table>
    <br>
    <div style="text-align: center">
        <input type="submit" value="SignIn">
    </div>
</form>
<br>
<div>
    <table align="center">
        <tr>
            <td colspan="2" style="text-align:center"><a href="createuser.jsp">Εισαγωγη νεου χρηστη</a></td>
        </tr>
        <tr>
            <td colspan="2" style="ext-align:center"><a href="./soft_eng.pdf">Βοήθεια</a></td>
        </tr>
    </table>
</div>
</body>
</html>
