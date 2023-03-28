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
  <body class="bg_default" style="background-color: #46178f;">

    <div class="pt-5 d-flex h-100 align-items-center justify-content-center">
      <div class="text-center">
        <h1 class="d-block pb-5" style="color: white;" >
          <img src="./images/bahoot-logo-transparent.png" width="500" height="500">
        </h1>
        <form method="get" action="selection.jsp">
          <input id="room_code" name="room_code" type="text" class="d-block text-center mt-3 " style="padding-left: 200px;padding-right: 200px;" placeholder="Enter Room Code!" maxLength="6" required>
          <button type="submit" class="btn btn-primary mt-5">Host Bahoot!</button>
        <form method="get" action="viewQuestions">
      </div>
    </div>
    <!---
    <div class="container-fluid fixed-bottom bg-light text-center ">
      <a class="m-0 p-3" href="selection.jsp">Host your own Bahoot!</a>
      
    </div> -->
    
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
  </body>
</html>
