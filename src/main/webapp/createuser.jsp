<%--
  Created by IntelliJ IDEA.
  User: vagel
  Date: 22/6/2021
  Time: 3:34 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <meta charset="UTF-8">
    <title>Create an account</title>
</head>
<style>
    body {background-color: #e6e6e6;}
</style>
<body>
<h1>Create an account</h1>
<form method="post" action="InsertNewStudent">
    <input type="hidden" name="requestType" value="Insert" />
    <table align="center"style="margin: 0px auto;">
        <tr>
            <td>Username:</td>
            <td><input type="text " required name="username" /></td>
            <br />
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" required name="password" /></td>
            <br />
        </tr>
        <tr>
            <td>Firstname:</td>
            <td><input type="text" required name="firstname" /></td>
            <br />
        </tr>
        <tr>
            <td>Lastname:</td>
            <td><input type="text" required name="lastname" /></td>
            <br />
        </tr>
        <tr>
            <td>Age:</td>
            <td><input type="number" required name="age" min="0" max="100" /></td>
            <br />
        </tr>
        <tr><td>
            Type of account<td><select onchange="visible()" id="typeacc" required name="typeacc">
            <option value="1" selected>Student</option>
            <option  value="2">Teacher</option>
        </select><br><br></tr>
            <td id="hiddentext" class="hiddentext" >6ψηφιος κωδικος:</td>
            <td><input id="hiddeninput"  type="text" minlength="6" maxlength="6" disabled name="code" /></td>
        </tr>
        <br/>
    </table>
    <br>
    <input type="submit" onsubmit="required()" value="Create User!" />
</form>
<script>
    function visible() {
        var e = document.getElementById("typeacc");
        var strUser = e.value;
        if (strUser == "2") {
            document.getElementById("hiddeninput").required=true;
            document.getElementById("hiddeninput").disabled = false;
        }
        else if(strUser=="1"){
            document.getElementById("hiddeninput").required=false;
            document.getElementById("hiddeninput").disabled = true;
        }
    }
</script>
</body>
</html>
