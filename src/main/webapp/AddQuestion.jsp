<%@ page import="com.example.BasicClasses.teacher" %><%--
  Created by IntelliJ IDEA.
  User: vagel
  Date: 10/7/2021
  Time: 4:32 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Questions - Teacher</title>
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
<h1>  <%= ((teacher) request.getSession(false).getAttribute("usr_obj")).getUsername() %>, assign new questions</h1>
<h3>Please select the difficulty and the preferred type of the question, then type the Question and the answers.</h3>
<form method="post" action="AddQuestions">
        <br>
        <br>
    <b>Question difficulty: </b>
    <select name="questdif">
        <option value="1" selected>1</option>
        <option>2</option>
        <option>3</option>
    </select><br><br></tr>
    <b>Type of question: </b>
    <label for="multichoice">Multiple choice</label>
    <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="multichoice" value="multichoice">
    <label for="filltheblank">Fill the blank</label>
    <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="filltheblank" value="filltheblank">
    <label for="trueorfalse">True or false</label>
    <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="trueorfalse" value="trueorfalse" required>
    <br>
    <br>
    <br>
    <div id="mc" style="display:none">
        <b>Question: </b><input type="text" id="idname1"  name="question1">
        <b>Choice 1: </b><input type="text" id="idname2" name="choice1">
        <b>Choice 2 (<u>correct</u>): </b><input type="text" id="idname3" name="choice_correct" style="border: 2px solid green;">
        <b>Choice 3: </b><input type="text" id="idname4" name="choice2" >
    </div>
    <div id="ftb" style="display: none">
        (Example) Question : Athens is the ________ of greece.        Anwser: capital
        <br>
        <b>Question: </b><input type="text" id="idname5" name="question2">
        <b>Anwser: </b><input type="text" id="idname6" name="anwser">
    </div>
    <div id="trf" style="display:none">
        <b>Question: </b><input id="idname7" type="text"  name="question3">
        <b>Answer: </b>
        <label for="True">True</label>
        <input type="radio" id="True" value="true"   name="first_item">
        <label for="False">False</label>
        <input type="radio" id="False" value="false" name="first_item">
    </div>
    <br>
    <input type="submit" value="Assign new question">
</form>
<script type="text/javascript">

    function yesnoCheck() {
        if (document.getElementById('multichoice').checked) {
            document.getElementById('mc').style.display = 'block';
            document.getElementById('ftb').style.display = 'none';
            document.getElementById('trf').style.display = 'none';
            document.getElementById("idname1").required = true;
            document.getElementById("idname2").required = true;
            document.getElementById("idname3").required = true;
            document.getElementById("idname4").required = true;
            document.getElementById("idname5").required = false;
            document.getElementById("idname6").required = false;
            document.getElementById('False').required = false;
            document.getElementById('idname7').required = false;
        }
        else if(document.getElementById('filltheblank').checked) {
            document.getElementById('mc').style.display = 'none';
            document.getElementById('ftb').style.display = 'block';
            document.getElementById('trf').style.display = 'none';
            document.getElementById("idname1").required = false;
            document.getElementById("idname2").required = false;
            document.getElementById("idname3").required = false;
            document.getElementById("idname4").required = false;
            document.getElementById("idname5").required = true;
            document.getElementById("idname6").required = true;
            document.getElementById('False').required = false;
            document.getElementById('idname7').required = false;
        }
        else if(document.getElementById('trueorfalse').checked){
            document.getElementById('mc').style.display = 'none';
            document.getElementById('ftb').style.display = 'none';
            document.getElementById('trf').style.display = 'block';
            document.getElementById('False').required = true;
            document.getElementById("idname1").required = false;
            document.getElementById("idname2").required = false;
            document.getElementById("idname3").required = false;
            document.getElementById("idname4").required = false;
            document.getElementById("idname5").required = false;
            document.getElementById("idname6").required = false;
            document.getElementById('idname7').required = true;
        }
    }

</script>


</body>
</html>
