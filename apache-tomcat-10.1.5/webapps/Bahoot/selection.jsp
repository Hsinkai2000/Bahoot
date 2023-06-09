<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ page import ="java.io.*,java.util.*,java.sql.*,java.text.*,java.lang.*"%> 
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
      String queryQuestions = "SELECT * FROM question_sets";
      ResultSet rs = stmt.executeQuery(queryQuestions);



      try {
        Statement insertStmt = conn.createStatement();
        String insertSQL = "INSERT INTO session (room_code, current_question_id) VALUES ('"+request.getParameter("room_code")+"',0)";
        insertStmt.executeUpdate(insertSQL);
      } catch (Exception e) {
        
      }
    %>
    <br>
    <br>
    <% String responseCode = request.getParameter("responseCode"); 
    String qrCodeUrl = "https://api.qrserver.com/v1/create-qr-code/?data=" + request.getParameter("room_code"); %>
    <img src="<%= qrCodeUrl %>" alt="QR Code" />
    <h1 style="color:aqua;"> Room Code: <%=request.getParameter("room_code")%> </h1>
    <h2 style="color: white;">Question Sets</h2>

    
    

    <div class="container">
      <div class="row">
        <% while (rs.next()) {%>
          <div class="col-sm-4 g-2">
            <div class="card h-100">
              <div class="card-body font-weight-bold">
                <h1 class="card-text"><%= rs.getString("name") %></h1>
              </div>
              <div class="card-footer">
                <form method="get" action="viewQuestions">
                  <input hidden name="setid" value="<%= rs.getInt("id") %>" >
                  <input hidden name="totalQn" value="<%= rs.getInt("totalQn") %>">
                  <input hidden name="room_code" value=<%=request.getParameter("room_code")%>>
                  <button type="submit" class="btn btn-info">Host</button>
                </form>
              </div>
            </div>
          </div>
        <%} %>
       
      </div>
    </div>
    
    <br>
    <br>


    <div class="row" style="height: 50px;">
      <div class="col-lg-8"></div>
      <div class="col-lg-2">
        <div class="row">
          <form class="col-lg-6" method="post" action="endSession">
            <input hidden name="room_code" value="<%= request.getParameter("room_code") %>">
                <button class="btn btn-danger h-100" type="submit" style="color: white;width: 100%;">End Session</button>               
          </form>    
          <div class="col-lg-6">
            <a href="createQuestion.jsp?room_code=<%=request.getParameter("room_code")%></a>"><button class="btn btn-primary">Create Question Set</button></a>
          </div>     
        </div>
          
      </div>
      <div class="col-lg-2"></div>
      
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
