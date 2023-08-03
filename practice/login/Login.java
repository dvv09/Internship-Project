/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.practice.login;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Asus
 */
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uemail = request.getParameter("username");
        String upwd = request.getParameter("password");
        String utype = request.getParameter("utype");
        
        RequestDispatcher dispatcher = null;
        RequestDispatcher dispatcher1 = null;
        RequestDispatcher dispatcher2 = null;
        
        HttpSession session = request.getSession();
        Connection con = null;
        
        if(uemail == null || uemail.equalsIgnoreCase("")){
                request.setAttribute("status", "Invalid Email");
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
        }
        if(upwd == null || upwd.equalsIgnoreCase("")){
                request.setAttribute("status", "Invalid Password");
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
        }
        if(utype == null || utype.equalsIgnoreCase("")||(!utype.equalsIgnoreCase("user") && !utype.equalsIgnoreCase("admin"))){
                request.setAttribute("status", "Invalid Type");
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
        }
        try {
            // Loading the mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creating the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");
            PreparedStatement pst = null;
            
            if(utype.equalsIgnoreCase("user")){
                
                    pst = con.prepareStatement("select * from user where uemail = ? and upwd = ?");
            
                    pst.setString(1,uemail);
                    pst.setString(2,upwd);
            }else{
              
                    pst = con.prepareStatement("select * from admin where uemail = ? and upwd = ?");
            
                    pst.setString(1,uemail);
                    pst.setString(2,upwd);
            }
            
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                if(utype.equalsIgnoreCase("user")){
                    
                        session.setAttribute("name", rs.getString("uname"));
                        dispatcher = request.getRequestDispatcher("user.jsp");
                        dispatcher1 = request.getRequestDispatcher("user.jsp");
                        dispatcher2 = request.getRequestDispatcher("user.jsp");
                }else{
                    
                        session.setAttribute("name", rs.getString("uname"));
                        dispatcher = request.getRequestDispatcher("admin.jsp");
                }
            }else{
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request, response);
            dispatcher1.forward(request, response);
            dispatcher2.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
