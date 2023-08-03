/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.library.admin;

import java.lang.NullPointerException;
import com.practice.login.RegistrationServlet;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Asus
 */
@MultipartConfig
public class AddBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String genre = request.getParameter("genre");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        //get image selected from the device
        Part part = request.getPart("coverpage");
        
        //get selected image file name
        String coverpage = part.getSubmittedFileName();
        
        //the path where we have to put the selected image in so that javvva can easily access it.
        String uploadPath = "C:/Users/Asus/Documents/NetBeansProjects/Library_Management/src/main/webapp/images/" + coverpage;
        
        //upload the select image in that folder that is specified on the uploadPath
        try {
                FileOutputStream fos = new FileOutputStream(uploadPath);
                InputStream is = part.getInputStream();

                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //uploading the image file name into the database and later using it to retrieve the image from the database
        RequestDispatcher dispatcher = null;
        Connection con = null;
        
        try {
            
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            
            PreparedStatement pst = con.prepareStatement("insert into books (Title,Author,Publisher,Genre,CoverPagePath,Quantity) values (?,?,?,?,?,?)");
            
            pst.setString(1,title);
            pst.setString(2,author);
            pst.setString(3,publisher);
            pst.setString(4,genre);
            pst.setString(5,coverpage);
            pst.setInt(6,quantity);
            
            int rowCount = pst.executeUpdate();
            
            dispatcher = request.getRequestDispatcher("abooks.jsp");
            if(rowCount > 0){
                request.setAttribute("status", "success");
                
            }
            else{
               request.setAttribute("status", "failed"); 
                
            }
            dispatcher.forward(request, response);
        } catch (IOException | ClassNotFoundException | SQLException | ServletException e) {
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}
