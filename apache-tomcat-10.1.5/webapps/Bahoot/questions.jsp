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
  <body class="bg_default" style="background-color: #46178f;">

    <div class="d-flex h-100 align-items-center justify-content-center">
        <div class="text-center">
          <h1 class="d-block pb-5" style="color: white;">
            <%= response.getHeader("question") %>
          </h1>
          <input id="name" type="text" class="d-block text-center mt-3" style="padding-left: 200px;padding-right: 200px;" placeholder="Enter your name">
          <button class="btn btn-primary mt-5">Enter Room</button>
        </div>
      </div>
    
      
   
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