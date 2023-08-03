/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.practice.login;

import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Asus
 */
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upwd = request.getParameter("pass");
        String umobile = request.getParameter("contact");
        String repwd = request.getParameter("re_pass");
        String utype = request.getParameter("utype");
        String uage = request.getParameter("uage");
        
         String pwd_regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
        
        RequestDispatcher dispatcher = null;
        Connection con = null;
        
        if(uname == null || uname.equalsIgnoreCase("")){
                request.setAttribute("status", "Invalid Name");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }
        else if(uname.length()<5){
                request.setAttribute("status", "Too Short Name");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }
        if(uemail == null || uemail.equalsIgnoreCase("")){
                request.setAttribute("status", "Invalid Email");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }
        if(upwd == null || upwd.equalsIgnoreCase("")){
                request.setAttribute("status", "Invalid Password");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }else if(upwd.length() < 6){
                request.setAttribute("status", "Invalid Pwd Length");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }else if(!upwd.matches(pwd_regex)){
                request.setAttribute("status", "Weak Pwd");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }
        if(!repwd.equals(upwd)){
                request.setAttribute("status", "Password not matched");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }
        if(umobile == null || umobile.equalsIgnoreCase("")){
                request.setAttribute("status", "Invalid Mobile");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }else if(umobile.length() > 10){
                request.setAttribute("status", "Invalid Mobile Length");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }
        if(utype == null || utype.equalsIgnoreCase("") ||(!utype.equalsIgnoreCase("user") && !utype.equalsIgnoreCase("admin"))){
                request.setAttribute("status", "Invalid Type");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }
                if(uage == null || uage.equalsIgnoreCase("")){
                request.setAttribute("status", "Please Enter Age");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
        }
        try {
            
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            PreparedStatement pst = null;
            
            if(utype.equalsIgnoreCase("user")){
                pst = con.prepareStatement("insert into user (uname,upwd,uage,uemail,umobile) values (?,?,?,?,?)");
                
            pst.setString(1,uname);
            pst.setString(2,upwd);
            pst.setString(3,uage);
            pst.setString(4,uemail);
            pst.setString(5,umobile);
            }
            else{
                pst = con.prepareStatement("insert into admin (uname,upwd,uage,uemail,umobile) values (?,?,?,?,?)");
                
            pst.setString(1,uname);
            pst.setString(2,upwd);
            pst.setString(3,uage);
            pst.setString(4,uemail);
            pst.setString(5,umobile);
            }
          
            
            int rowCount = pst.executeUpdate();
            
            dispatcher = request.getRequestDispatcher("registration.jsp");
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
