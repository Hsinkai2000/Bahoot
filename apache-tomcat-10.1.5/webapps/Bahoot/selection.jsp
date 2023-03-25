<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ page import ="java.io.*,java.util.*,java.sql.*,java.text.*"%> 
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Bahoot!</title>
    
    <link rel="icon" href="./images/bahoot.ico" type="image/x-icon">
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
      crossorigin="anonymous"
    />
    <link href="./style/style.css" rel="stylesheet" />
  </head>
  <body class="bg_default" style="background-color: #46178f; padding-left: 100px;padding-right: 100px;">
    <nav class="navbar navbar-light" style="background-color: #46178f">
      <a class="navbar-brand " href="index.jsp">
      <img
          src="./images/bahoot-logo-transparent.png"
          width="150dp"
          height="150dp"
        />
      </a>
    </nav>
    <% try {
      Connection conn = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
              "root", "password");

      Statement stmt = conn.createStatement();
      String queryQuestions = "SELECT * FROM qnSets";
      ResultSet rs = stmt.executeQuery(queryQuestions);
    %>
    <br>
    <br>
    <h2 style="color: white;">Question Sets</h2>
    <div class="pt-3">
      <div class="row row-cols-1 row-cols-md-6 g-4">
        <% while (rs.next()) {%>
          <div class="col">
            <div class="card h-100">
              <div class="card-body">
                <h1 class="card-text"><%= rs.getString("name") %></h1>
              </div>
              <div class="card-footer">
                <form method="get" action="viewQuestions">
                  <input hidden name="setid" value="<%= rs.getInt("id") %>" >
                  <input hidden name="totalQn" value="<%= rs.getInt("totalQn") %>">
                  <button type="submit" class="btn btn-info">Host</button>
                </form>
              </div>
            </div>
          </div>
        <%}%>
      </div>
    </div>

    <br>
    <br>
    <br>
    <footer class="text-white mt-5 p-4 text-center">
        Copyright &copy; 2023 Bahoot!. All Rights Reserved
    </footer>
    
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
      crossorigin="anonymous"
    >
    </script>
    <script>
      function loadServlet() {
        // Creating an XMLHttpRequest object to handle request
        var xhr = new XMLHttpRequest();
  
        // Setting up the request method and URL
        xhr.open("GET", "http://localhost:9999/Bahoot/viewQuestions", true);
  
        // Sending the request
        xhr.send();
      }
    </script>

    <%
  } catch (Exception e) {
  } %>
  </body>
</html>
