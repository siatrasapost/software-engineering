<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body {background-color: #e6e6e6;}
    </style>
    <title>Connect</title>
</head>
<body>
<form method="post" action="MyServlet">
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
