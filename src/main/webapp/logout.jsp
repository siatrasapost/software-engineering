<%@ page import="com.example.TelikiErgasiaTexnLogismikou.MyServlet" %><%--
  Created by IntelliJ IDEA.
  User: siatr
  Date: 7/7/2021
  Time: 1:46 μ.μ.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    response.addHeader("Cache-Control", "post-check=0, pre-check=0");

    //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0);

    //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma", "no-cache");

    if ((String)request.getSession(false).getAttribute("online")==null || !((String)request.getSession(false).getAttribute("acctype")).equals("1")){
        response.sendRedirect("index.jsp");
    }

%>
    <p>Are you sure you want to logout?</p>
    <form method="get" action="AccountHandling">
        <input type="submit" value="Yes">
    </form>
    <form method="post" action="MyServlet">
        <input type="submit" value="No">
    </form>
</body>
</html>
