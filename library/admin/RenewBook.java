/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.library.admin;

import java.lang.Integer;
import java.io.*;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Asus
 */
public class RenewBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
            String Intid = request.getParameter("bookId");
            int Id = Integer.parseInt(Intid);
            
            String rdate = request.getParameter("rdate");
            java.sql.Date DueDate = java.sql.Date.valueOf(rdate);

             out.println(Id);
             out.println(DueDate);

        RequestDispatcher dispatcher = null;
        Connection con = null;
        dispatcher = request.getRequestDispatcher("ucollection.jsp");
        
        try {
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            
             PreparedStatement psth = con.prepareStatement("UPDATE issuebook SET RDate = ? WHERE Id = ?");
             psth.setDate(1,DueDate);
             psth.setInt(2,Id);
             
             PreparedStatement pst = con.prepareStatement("UPDATE history SET RDate = ? WHERE Id = ?");
             pst.setDate(1,DueDate);
             pst.setInt(2,Id);
             
            int hcount = psth.executeUpdate();
            int rowCount = pst.executeUpdate();

            if(rowCount > 0 && hcount > 0 ){
                request.setAttribute("status", "success");
                
            }
            else{
               request.setAttribute("status", "failed"); 
                
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
        }
    }
}
