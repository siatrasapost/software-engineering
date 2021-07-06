package com.example.TelikiErgasiaTexnLogismikou;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Logout", value = "/Logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma","no-cache");
        session.invalidate();
        response.sendRedirect("index.jsp");
    }

}
