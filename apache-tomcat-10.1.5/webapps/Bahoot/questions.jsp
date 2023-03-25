<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ page import ="java.io.*,java.util.*,java.sql.*,java.text.*"%> 
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Old Egg | Home</title>
    
    <link rel="icon" href="./images/oldegg-icon.ico" type="image/x-icon">
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
      crossorigin="anonymous"
    />
    <link href="./style/style.css" rel="stylesheet" />
  </head>
  <body class="bg_default" style="background-color: #46178f;padding-left: 100px;padding-right: 100px;">
    <nav class="navbar navbar-light" style="background-color: #46178f;">
      <a class="navbar-brand " href="index.jsp"><h1 style="color: white;">Bahoot!</h1></a>
    </nav>
      <div class="container pt-5">
        <div class="row">
          <div class="col-lg-3"></div>
          <div class="col-lg-6">
            <h1 class="d-block pb-5" style="color: white;">
              <%= response.getHeader("qnNo")%>) &nbsp;&nbsp;&nbsp;<%= response.getHeader("question") %>
            </h1>
          </div>
          <div class="col-lg-3"></div>
        </div>
        <div class="row" style="height: 200px;">
          <div class="col-lg-2"></div>
          <div class="col-lg-4">
            <button type="button" style="width: 100%; height: 100%; font-size: xx-large;" class="btn btn-primary"><%= response.getHeader("opt1") %></button>
          </div>
          <div class="col-lg-4">
            <button type="button" style="width: 100%; height: 100%; font-size: xx-large;" class="btn btn-success"><%= response.getHeader("opt2") %></button>
          </div>
          <div class="col-lg-2"></div>
        </div>
        
        <br>

        <div class="row" style="height:200px;">
          <div class="col-lg-2"></div>
          <div class="col-lg-4">
            <button type="button" style="width: 100%; height: 100%; font-size: xx-large;" class="btn btn-info"><%= response.getHeader("opt3") %></button>
          </div>
          <div class="col-lg-4">
            <button type="button" style="width: 100%; height: 100%; font-size: xx-large;" class="btn btn-warning"><%= response.getHeader("opt4") %></button>
          </div>
          <div class="col-lg-2"></div>
        </div>

        <br>
        <br>
        <br>
        <div class="row" style="height:200px;">
          <div class="col-lg-2"></div>
          <div class="col-lg-6"></div>
          <div class="col-lg-2">
            <form method="get" action="viewQuestions">
              <input hidden name="qnNo" value="<%= Integer.parseInt(response.getHeader("qnNo")) +1 %>" >
              <input hidden name="setid" value="<%= response.getHeader("setid") %>" >
              <input hidden name="totalQn" value="<%= response.getHeader("totalQn") %>">
              <button class="btn btn-danger" style="width: 100%;">Next Question</button>
            </form>            
          </div>
          <div class="col-lg-2"></div>
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
  </body>
</html>
