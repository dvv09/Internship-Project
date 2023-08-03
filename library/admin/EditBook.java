/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.library.admin;

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
public class EditBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = null;
        Connection con = null;
        dispatcher = request.getRequestDispatcher("abooks.jsp");
        
        String IntId = request.getParameter("bookId");
        int bookId = Integer.parseInt(IntId);
        
        String title = request.getParameter("title");
        
        String Intq = request.getParameter("quantity");
        int quantity = Integer.parseInt(Intq);
        
        String author = request.getParameter("author");
        
        String publisher = request.getParameter("publisher");
        
        String genre = request.getParameter("genre");
        
        String edit = request.getParameter("edit");
        
        if(edit.equalsIgnoreCase("update")){
                    try {
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            
            PreparedStatement pst = con.prepareStatement("UPDATE books set Title = ? ,Author = ? ,Publisher = ? ,Genre = ? ,Quantity = ? where BookID = ?");
            
            pst.setString(1,title);
            pst.setString(2,author);
            pst.setString(3,publisher);
            pst.setString(4,genre);
            pst.setInt(5,quantity);
            pst.setInt(6,bookId);
            
            int rowCount = pst.executeUpdate();
            
            if(rowCount > 0){
                request.setAttribute("status", "usuccess");
                
            }
            else{
               request.setAttribute("status", "ufailed"); 
                
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
        }
        }
        else{
                    try {
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            
            PreparedStatement pst = con.prepareStatement("DELETE from books where BookID = ?");
            
            pst.setInt(1,bookId);
            
            PreparedStatement adjust = con.prepareStatement("UPDATE books SET BookID = BookID - 1 WHERE BookID > ?");
            
            adjust.setInt(1,bookId);
            
            int rowCount = pst.executeUpdate();
            int rowCount2 = adjust.executeUpdate();
            
            if(rowCount > 0 && rowCount2 > 0 ){
                request.setAttribute("status", "dsuccess");
                
            }
            else{
               request.setAttribute("status", "dfailed"); 
                
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
        }
        }
        
    }


}
