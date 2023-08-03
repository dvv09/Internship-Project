package com.library.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IssueBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = null;
        Connection con = null;
        dispatcher = request.getRequestDispatcher("user.jsp");

        LocalDate currentDate = LocalDate.now();
        java.sql.Date currDate = java.sql.Date.valueOf(currentDate);
        // Retrieve form data
        String IntId = request.getParameter("bookId");
        int bookId = Integer.parseInt(IntId);
        String name = request.getParameter("name");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String genre = request.getParameter("genre");
        String rdate = request.getParameter("rdate");
        java.sql.Date returnDate = java.sql.Date.valueOf(rdate);
        String quantity = request.getParameter("quantity");
        int qnty = Integer.parseInt(quantity);
        // Perform desired operations with the form data
        // ...
        
        PrintWriter out = response.getWriter();
        
        if(qnty == 0){ 
            request.setAttribute("status", "Book Not Available");
            dispatcher.forward(request, response);   
        }
        
        try {
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            
            PreparedStatement pst = con.prepareStatement("insert into issuebook (Name,Title,Author,IDate,RDate) values (?,?,?,?,?)");
            
            pst.setString(1,name);
            pst.setString(2,title);
            pst.setString(3,author);
            pst.setDate(4,currDate);
            pst.setDate(5,returnDate);
            
            PreparedStatement psth = con.prepareStatement("insert into history (Name,Title,Author,IDate,RDate) values (?,?,?,?,?)");
            
            psth.setString(1,name);
            psth.setString(2,title);
            psth.setString(3,author);
            psth.setDate(4,currDate);
            psth.setDate(5,returnDate);
            
            PreparedStatement reducequantity = con.prepareStatement("UPDATE books SET Quantity = Quantity - 1 WHERE BookID = ?");
            
            reducequantity.setInt(1,bookId);
                    
            int hcount = psth.executeUpdate();
            int rowCount = pst.executeUpdate();
            int quantitycnt = reducequantity.executeUpdate();
            
            dispatcher = request.getRequestDispatcher("user.jsp");
            if(rowCount > 0 && hcount > 0 && quantitycnt > 0){
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

