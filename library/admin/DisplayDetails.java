/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.library.admin;

import com.practice.login.RegistrationServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.swing.UIManager.getInt;


/**
 *
 * @author Asus
 */
public class DisplayDetails extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        String imageId = request.getParameter("id");
        int id = Integer.parseInt(imageId);
        
                Connection con = null;
                int imgId = 0;
                String imgFileName = null;
        
        try {
            
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            
            Statement st;
            String q = "select * from books";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
                if(rs.getInt("BookID") == id){
                    imgId = rs.getInt("BookID");
                    imgFileName = rs.getString("CoverPagePath");
                    PrintWriter out = response.getWriter();
                    out.println(imgId);
                    out.println(imgFileName);
                }
            }
            
        } catch (ClassNotFoundException | SQLException  e) {
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        RequestDispatcher rd;
        request.setAttribute("id", String.valueOf(imgId));
        request.setAttribute("img", imgFileName);
        
        rd = request.getRequestDispatcher("admin.jsp");
        rd.forward(request, response);
    }


}
