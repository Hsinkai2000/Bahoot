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
    <br>
    <br>

    <div class="container">
        <form method="get" action="NewQnSet">
            <% if(request.getParameter("numQuestion")!=null){%>
                <div class="form-group">
                <label for="setName" style="color: white;">Set Name</label>
                <input type="text" class="form-control" id="setName" name="setName" placeholder="Enter Set Name">
                </div>
            <%}%>
            <div class="form-group">
              <label <% if(request.getParameter("numQuestion")!=null){%>style="display:none;"<%}%>for="numQuestions" style="color: white;">Number of Questions</label>
              <input <% if(request.getParameter("numQuestion")!=null){%>hidden value=<%=Integer.parseInt(request.getParameter("numQuestion"))%><%}%> type="number" class="form-control" id="numQuestions" name="numQuestions" placeholder="Enter Number of Questions">
            </div>
            <br>
            <%if(request.getParameter("numQuestion") != null){
                 for (int i = 1; i <= Integer.parseInt(request.getParameter("numQuestion")); i++) { %>
                
                  <div class="form-group">
                    <label style="color: white;" for="question<%=i%>">Question<%=i%></label>
                    <input type="text" class="form-control" id="question<%=i%>" name="question<%=i%>" placeholder="Enter Question">
                  </div>
                  <div class="form-group">
                    <label style="color: white;" for="option1<%=i%>">Option 1</label>
                    <input type="text" class="form-control" id="option1<%=i%>" name="option1_<%=i%>" placeholder="Enter Option 1">
                  </div>
                  <div class="form-group">
                    <label style="color: white;" for="option2<%=i%>">Option 2</label>
                    <input type="text" class="form-control" id="option2<%=i%>" name="option2_<%=i%>" placeholder="Enter Option 2">
                  </div>
                  <div class="form-group">
                    <label style="color: white;" for="option3<%=i%>">Option 3</label>
                    <input type="text" class="form-control" id="option3<%=i%>" name="option3_<%=i%>" placeholder="Enter Option 3">
                  </div>
                  <div class="form-group">
                    <label style="color: white;" for="option4<%=i%>">Option 4</label>
                    <input type="text" class="form-control" id="option4<%=i%>" name="option4_<%=i%>" placeholder="Enter Option 4">
                  </div>
                  <div class="form-group">
                    <label style="color: white;" for="correctOption<%=i%>">Correct Option</label>
                    <input type="text" class="form-control" id="correctOption<%=i%>" name="correctOption<%=i%>" placeholder="Enter Correct Option">
                  </div>
                  <br>
            <%}};%>
            <button type="submit" class="btn btn-primary">Submit</button>
          </form>
          <script>
            var numQuestions = document.getElementById('numQuestions');
            const questionsContainer = document.getElementById('questionsContainer');
            
            numQuestions.addEventListener('change', () => {
              window.location.href = "http://localhost:9999/Bahoot/createQuestion.jsp?numQuestion="+numQuestions.value;
            });
          </script>   
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
  </body>
</html>
