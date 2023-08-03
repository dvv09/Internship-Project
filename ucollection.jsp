<%-- 
    Document   : mycollection
    Created on : 6 Jun, 2023, 8:46:21 AM
    Author     : Asus
--%>

<%
    if(session.getAttribute("name")==null){
        response.sendRedirect("login.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Library Management System</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

  <style>
    /* Add your custom CSS styling here */

    /* Example styles to enhance visibility */
    body {
      padding-top: 0px;
    }
    h1 {
      margin-top: 30px;
    }
    .navbar-dark .navbar-nav .nav-link {
    color: rgba(255,255,255,.5);
    margin-right: 2.25rem;
    margin-left: 2.25rem;
}
.navbar-brand {
    margin-right: 5rem;
}
  </style>

</head>
<body>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="user.jsp">Library</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="udash.jsp">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ucollection.jsp">My Collection</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="uhistory.jsp">History</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Logout">Logout</a>
                </li>
            </ul>
        </div>
    </nav>


<%@ page import="java.sql.*" %>
<%@ page import="java.util.Date" %>

<div class="group">
    <table class="table">
        <thead>
            <tr>
                <th scope="col">Book Name</th>
                <th scope="col">Author</th>
                <th scope="col">Issue Date</th>
                <th scope="col">Due Date</th>
                <th scope="col">Return Date</th>
                <th scopre="cool">  </th>
            </tr>
        </thead>
        <tbody>
            <% 
            Date currentDate = new Date();
            
            // Establish the database connection
            Connection connection = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Vr@j1803");

                // Query the database for book information
                String name = (String) session.getAttribute("name");
                String query = "SELECT * FROM issuebook WHERE Name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();

                int count = 0;
                // Iterate through the result set and display the book data
                while (resultSet.next()) {
                    String Intid = resultSet.getString("Id");
                    int Id = Integer.parseInt(Intid);
                    String Title = resultSet.getString("Title");
                    String Author = resultSet.getString("Author");
                    String IDate = resultSet.getString("IDate");
                    String RDate = resultSet.getString("RDate");
                    String AcRDate = resultSet.getString("AcRDate");
            %>
            <tr>
                <td><%= Title %></td>
                <td><%= Author %></td>
                <td><%= IDate %></td>
                <td><%= RDate %></td>
                <td><%= "Not Returned" %></td>
                <td>
                <button type="button" data-toggle="modal" data-target="#renew-book-modal-<%= Id %>" style="display:block;"> Renew Book</button>

                    <!-- Modal -->
            <div class="modal fade" id="renew-book-modal-<%= Id %>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-md" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Book Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
                        <form action="RenewBook" method="POST">
                            <input type="hidden" name="bookId" value="<%= Id %>">
                            The Previous Due Date was <%= RDate %> 
                            <div class="form-group">
                                <label for="genre">New Due Date:</label>
                                <input type="date" class="form-control" id="rdate" name="rdate"  required/>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">ReNew Book</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
                </td>
            </tr>
            <% 
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Close the database connection
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            %>
        </tbody>
    </table>
</div>

  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  
    <!--++++++++++++++++++++++++ SCRIPTS FOR SWEET ALERT +++++++++++++++++++++++++++++++++++-->
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  <link rel="stylesheet" href="alert/dist/sweetalert.css">
  
    <!--+++++++++++++++ USING THE VALUES STORED IN HIDDEN INPUT FIELD NAMED STATUS +++++++++++++++++++++++-->
        <script>
            var status = document.getElementById("status").value;
                        if(status == "success"){
                swal("Congrats","Book is successfully Renewed","success");
            }
                        if(status == "failed"){
                swal("Sorry","There is some error","error");
            }
        </script>
    <!--+++++++++++++++ END OF USING THE VALUES STORED IN HIDDEN INPUT FIELD NAMED STATUS +++++++++++++++++++++++-->
</body>

</html>

