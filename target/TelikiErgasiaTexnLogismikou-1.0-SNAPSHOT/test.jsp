<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.BasicClasses.student" %>
<%--
  Created by IntelliJ IDEA.
  User: siatr
  Date: 27/6/2021
  Time: 5:46 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Test</title>
</head>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "no-cache");
    String userName = ((student) session.getAttribute("usr_obj")).getUsername();
    String acctype = (String) session.getAttribute("acctype");
    if (null == userName || !acctype.equals("1")) {
        out.println("<script type=text/javascript>");
        out.println("alert(\"Please login to continue!\");");
        out.println("location.replace(\"index.jsp\")");
        out.println("</script>");
        System.out.println(userName+acctype);
    }
%>
    <h1>Test</h1>
    <form method="post" action="test">
        <%
            int j=0;
            for (String s : (List<String>)request.getAttribute("questions")
                 ) {
                out.println("<p>"+s+"</p>");
                out.println("<label>");
                //out.println("nai<input type=\"radio\" value=\"true\"  required name=\"radio"+j+"\"/>");
                //out.println("oxi<input type=\"radio\"  value=\"false\" name=\"radio"+j+"\" required=\"\"/>");
                out.println("<input type=\"text\" id=\"textbox"+j+"\" name=\"textbox"+j+"\" placeholder=\"Type your answer...\" size=\"30\" required>");
                out.println("</label>");

                j++;
            }
        %>
        <br>
        <br>
        <input type="hidden" name="id_test" id="id_test" value="<%= request.getAttribute("test_id")%>">
        <input type="submit" value="Submit Answers">
    </form>
    <a href="Logout">Log Out</a>
</body>
</html>
