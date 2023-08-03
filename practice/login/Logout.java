/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.practice.login;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
 *
 *
 * @author Asus
 */

public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");
        
    }
}
