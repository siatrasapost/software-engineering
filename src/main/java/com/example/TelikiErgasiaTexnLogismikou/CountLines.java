package com.example.TelikiErgasiaTexnLogismikou;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountLines {
    public int Count() throws ServletException {
        DataSource datasource = null;
        int i=0;
        try {

            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
        } catch(Exception e) {
            throw new ServletException(e.toString());
        }
        try {
            Connection con = datasource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while(rs.next()) {
                String username = rs.getString("username");
                String surName = rs.getString("firstname");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public int CountClient() throws ServletException {
        DataSource datasource = null;
        int i=0;
        try {

            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
        } catch(Exception e) {
            throw new ServletException(e.toString());
        }
        try {
            Connection con = datasource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM client");
            while(rs.next()) {
                String username = rs.getString("username");
                String surName = rs.getString("firstname");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}

