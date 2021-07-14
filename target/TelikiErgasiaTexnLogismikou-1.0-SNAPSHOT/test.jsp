<%@ page import="java.util.*" %>
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
    <title>Test - Student</title>
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
        <span class="logo">Test</span>
        <div class="header-left">
            <a class="home" href="./student_home.jsp">Home</a>
        </div>
        <div class="header-right">
            <a class="logout" href="./logout.jsp">Logout</a>
        </div>
    </div>
    <h3>Please fill out the test before submitting it!</h3>
    <form method="post" action="test">
        <%
            int j=0;
            for (String s : (List<String>)request.getAttribute("questions")
                 ) {
                out.println("<p>"+(j+1)+". "+s.split(",")[0]+"</p>");
                out.println("<label>");
                switch (((List<Integer>)request.getAttribute("types")).get(j)){
                    case 1:
                        List<String> multiple = Arrays.asList(s.split(","));
                        Collection<String> c = new ArrayList(multiple);
                        c.remove(new String(multiple.get(0)));

                        Collections.shuffle((List<String>) c);

                        out.println("<input type=\"radio\" id=\"radio"+j+"1\" name=\"textbox"+j+"\" value=\""+ ((ArrayList<String>) c).get(0) +"\">"+ ((ArrayList<String>) c).get(0));
                        out.println("<input type=\"radio\" id=\"radio"+j+"2\" name=\"textbox"+j+"\" value=\""+ ((ArrayList<String>) c).get(1) +"\">"+ ((ArrayList<String>) c).get(1));
                        out.println("<input type=\"radio\" id=\"radio"+j+"3\" name=\"textbox"+j+"\" value=\""+ ((ArrayList<String>) c).get(2) +"\">"+ ((ArrayList<String>) c).get(2));
                        break;
                    case 2:
                        out.println("<input type=\"text\" id=\"textbox"+j+"\" name=\"textbox"+j+"\" placeholder=\"Type your answer...\" size=\"30\" required>");
                        break;
                    case 3:
                        out.println("<input type=\"radio\" id=\"radio"+j+"1\" name=\"textbox"+j+"\" value=\"true\"> True");
                        out.println("<input type=\"radio\" id=\"radio"+j+"2\" name=\"textbox"+j+"\" value=\"false\"> False");
                        break;
                }
                out.println("</label>");

                j++;
            }
        %>
        <br>
        <br>
        <input type="hidden" name="id_test" id="id_test" value="<%= request.getAttribute("test_id")%>">
        <input type="submit" value="Submit Answers">
    </form>
</body>
</html>
