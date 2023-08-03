/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.library.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Asus
 */
public class ReturnBook extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Intid = request.getParameter("Id");
        int Id = Integer.parseInt(Intid);
        
        RequestDispatcher dispatcher = null;
        Connection con = null;
        dispatcher = request.getRequestDispatcher("ausers.jsp");
        
        PrintWriter out = response.getWriter();

        LocalDate currentDate = LocalDate.now();
        java.sql.Date currDate = java.sql.Date.valueOf(currentDate);
        
        try {
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            
             PreparedStatement psth = con.prepareStatement("UPDATE history SET AcRDate = ? WHERE Id = ?");
             psth.setDate(1,currDate);
             psth.setInt(2,Id);
             
             PreparedStatement pst = con.prepareStatement("Delete  from issuebook WHERE Id = ?");
             pst.setInt(1,Id);
             
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
