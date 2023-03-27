<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ page import ="java.io.*,java.util.*,java.sql.*,java.text.*"%> 
<%

    int questionCount = 0;
    ArrayList<String> questionArray = new ArrayList<String>();
    ArrayList<String> option1Array = new ArrayList<String>();
    ArrayList<String> option2Array = new ArrayList<String>();
    ArrayList<String> option3Array = new ArrayList<String>();
    ArrayList<String> option4Array = new ArrayList<String>();
    ArrayList<String> correctOptArray = new ArrayList<String>();

    ArrayList<Statement> responseStmtArray = new ArrayList<Statement>();
    ArrayList<String> queryResponseArray = new ArrayList<String>();
    ArrayList<ResultSet> responseRSArray = new ArrayList<ResultSet>();

    ArrayList<HashMap<Integer,Integer>> hashArray = new ArrayList<HashMap<Integer,Integer>>();
    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

    ArrayList<Integer> totalCountArray = new ArrayList<Integer>();
    ArrayList<Integer> option0CountArray = new ArrayList<Integer>();
    ArrayList<Integer> option1CountArray = new ArrayList<Integer>();
    ArrayList<Integer> option2CountArray = new ArrayList<Integer>();
    ArrayList<Integer> option3CountArray = new ArrayList<Integer>();
    ArrayList<Integer> option4CountArray = new ArrayList<Integer>();



    //ArrayList<String> totalResponseArray = new ArrayList<String>();

    

    try {
      Connection conn = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
              "root", "password");

      Statement stmt = conn.createStatement();
      String queryQuestions = "SELECT * FROM questions where setID='1'";
      ResultSet rs = stmt.executeQuery(queryQuestions);


    

      while (rs.next()) {
        ++questionCount;
        questionArray.add(rs.getString("question"));
        option1Array.add(rs.getString("opt1"));
        option2Array.add(rs.getString("opt2"));
        option3Array.add(rs.getString("opt3"));
        option4Array.add(rs.getString("opt4"));
        correctOptArray.add(rs.getString("correctOpt"));
      }

      for (int i = 0; i < questionCount; i++) {
        responseStmtArray.add(conn.createStatement());
        queryResponseArray.add("SELECT choice, COUNT(*) AS count FROM responses WHERE roomCode='774537' AND questionSetID = '1' AND questionNo='" + (i+1) + "' GROUP BY choice");
        
        responseRSArray.add(responseStmtArray.get(i).executeQuery(queryResponseArray.get(i)));

      }
        
      
     

      for (int i = 0; i < questionCount; i++) {
        int totalCount = 0;
        int option0Count = 0;
        int option1Count = 0;
        int option2Count = 0;
        int option3Count = 0;
        int option4Count = 0;

        while (responseRSArray.get(i).next()) {
            if (responseRSArray.get(i).getInt("choice") == 0)
                option0Count = responseRSArray.get(i).getInt("count");
            if (responseRSArray.get(i).getInt("choice") == 1)
                option1Count = responseRSArray.get(i).getInt("count");
            if (responseRSArray.get(i).getInt("choice") == 2)
                option2Count = responseRSArray.get(i).getInt("count");
            if (responseRSArray.get(i).getInt("choice") == 3)
                option3Count = responseRSArray.get(i).getInt("count");
            if (responseRSArray.get(i).getInt("choice") == 4)
                option4Count = responseRSArray.get(i).getInt("count");

            totalCount += responseRSArray.get(i).getInt("count");
        }
        totalCountArray.add(totalCount);
        option0CountArray.add(option0Count);
        option1CountArray.add(option1Count);
        option2CountArray.add(option2Count);
        option3CountArray.add(option3Count);
        option4CountArray.add(option4Count);


       
      } 





    

    } catch (Exception e) {

    }




    int q1c1 = Integer.parseInt(response.getHeader("q1c1"));
    int q1c2 = Integer.parseInt(response.getHeader("q1c2"));
    int q1c3 = Integer.parseInt(response.getHeader("q1c3"));
    int q1c4 = Integer.parseInt(response.getHeader("q1c4"));
    int q2c1 = Integer.parseInt(response.getHeader("q2c1"));
    int q2c2 = Integer.parseInt(response.getHeader("q2c2"));
    int q2c3 = Integer.parseInt(response.getHeader("q2c3"));
    int q2c4 = Integer.parseInt(response.getHeader("q2c4"));
    int q3c1 = Integer.parseInt(response.getHeader("q3c1"));
    int q3c2 = Integer.parseInt(response.getHeader("q3c2"));
    int q3c3 = Integer.parseInt(response.getHeader("q3c3"));
    int q3c4 = Integer.parseInt(response.getHeader("q3c4"));

    String setName = response.getHeader("setName");

    int totalq1 = q1c1+q1c2+q1c3+q1c4;
    int totalq2 = q2c1+q2c2+q2c3+q2c4;
    int totalq3 = q3c1+q3c2+q3c3+q3c4;


    double q1c1px = ((double)q1c1/totalq1) * 100;
    double q1c2px = ((double)q1c2/totalq1) * 100;
    double q1c3px = ((double)q1c3/totalq1) * 100;
    double q1c4px = ((double)q1c4/totalq1) * 100;
    double q2c1px = ((double)q2c1/totalq2) * 100;
    double q2c2px = ((double)q2c2/totalq2) * 100;
    double q2c3px = ((double)q2c3/totalq2) * 100;
    double q2c4px = ((double)q2c4/totalq2) * 100;
    double q3c1px = ((double)q3c1/totalq3) * 100;
    double q3c2px = ((double)q3c2/totalq3) * 100;
    double q3c3px = ((double)q3c3/totalq3) * 100;
    double q3c4px = ((double)q3c4/totalq3) * 100;    
%>
<% System.out.println("debug1 = "+ q1c3px); %>
<% System.out.println("debug2 = "+ (((double)option3CountArray.get(0)/totalCountArray.get(0)) * 100)); %>
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
  <body class="bg_default" style="background-color: #46178f;padding-left: 100px;padding-right: 100px;">
    <nav class="navbar navbar-light" style="background-color: #46178f;">
      <a class="navbar-brand " href="index.jsp">
      <img
          src="./images/bahoot-logo-transparent.png"
          width="150dp"
          height="150dp"
        />
      </a>
    </nav>

   

      
    <h2 class="pb-3 pt-5" style="color: white;">Statistics for <%=setName%></h2>

    <%for (int i = 0; i < questionCount; i++) {%>
        <%
            double option0px = (((double)option1CountArray.get(i)/totalCountArray.get(i)) * 100);
            double option1px = (((double)option1CountArray.get(i)/totalCountArray.get(i)) * 100);
            double option2px = (((double)option2CountArray.get(i)/totalCountArray.get(i)) * 100);
            double option3px = (((double)option3CountArray.get(i)/totalCountArray.get(i)) * 100);
            double option4px = (((double)option4CountArray.get(i)/totalCountArray.get(i)) * 100);
        
        %>
        <div id="qn1">
            <h3 style="color:white;"><%=(i+1)%>) <%=questionArray.get(i)%></h3>
            <br>
            <div class="row">
                <div class="col-1">
                    <button class="btn btn-primary" style="color:white;">A:</button> 
                   
                     
                </div>
                <div class="col-5 text-left">
                    <p style="color:<% if (correctOptArray.get(i).matches("1")) {%>greenYellow<%} else {%> white <%}%>;"><%=option1Array.get(i)%></p>
                </div>
            </div>
            <div class="row">
                <div class="col-1">
                    <button class="btn btn-success" style="color:white;">B:</button> 
                  
                </div>
                <div class="col-5 text-left">
                    <p style="color:<% if (correctOptArray.get(i).matches("2")) {%>greenYellow<%} else {%> white <%}%>;"><%=option2Array.get(i)%></p>
                </div>
            </div>
            <div class="row">
                <div class="col-1">
                    <button class="btn btn-info" style="color:white;">C:</button> 
                    
                </div>
                <div class="col-5 text-left">
                    <p style="color:<% if (correctOptArray.get(i).matches("3")) {%>greenYellow<%} else {%> white <%}%>;"><%=option3Array.get(i)%></p>
                </div>
            </div>
            <div class="row">
                <div class="col-1">
                    <button class="btn btn-warning" style="color:white;">D:</button> 
                    
                </div>
                <div class="col-5 text-left">
                    <p style="color:<% if (correctOptArray.get(i).matches("4")) {%>greenYellow<%} else {%> white <%}%>;"><%=option4Array.get(i)%></p>
                </div>
            </div>

             <div class="chart-wrap vertical">      
            <div class="progress" style="height: 30px;">
                <div class="progress-bar" role="progressbar" style="width: <%= option1px %>%; height:100%;" aria-valuenow="<%= option1px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= option1CountArray.get(i) %></h5></div>
                <div class="progress-bar bg-success" role="progressbar" style="width: <%= option2px %>%" aria-valuenow="<%= option2px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= option2CountArray.get(i) %></h5></div>
                <div class="progress-bar bg-info" role="progressbar" style="width: <%= option3px %>%" aria-valuenow="<%= option3px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= option3CountArray.get(i) %></h5></div>
                <div class="progress-bar bg-warning" role="progressbar" style="width: <%= option4px %>%" aria-valuenow="<%= option4px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= option4CountArray.get(i) %></h5></div>
            </div>
        </div>

            
        </div>
        <br>
        <br>
    <%}%>

    <div id="qn1">
        <h3 style="color:white;">1) If a super class has a total of 10 instances, what is the total number of instances of subclasses can there be?</h3>
        <br>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-primary" style="color:white;">A:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">3</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-success" style="color:white;">B:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">6</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-info" style="color:white;">C:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:greenyellow;">10</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-warning" style="color:white;">D:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">14</p>
            </div>
        </div>

        <div class="chart-wrap vertical">      
            <div class="progress" style="height: 30px;">
                <div class="progress-bar" role="progressbar" style="width: <%= q1c1px %>%; height:100%;" aria-valuenow="<%= q1c1px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q1c1 %></h5></div>
                <div class="progress-bar bg-success" role="progressbar" style="width: <%= q1c2px %>%" aria-valuenow="<%= q1c2px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q1c2 %></h5></div>
                <div class="progress-bar bg-info" role="progressbar" style="width: <%= q1c3px %>%" aria-valuenow="<%= q1c3px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q1c3 %></h5></div>
                <div class="progress-bar bg-warning" role="progressbar" style="width: <%= q1c4px %>%" aria-valuenow="<%= q1c4px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q1c4 %></h5></div>
            </div>
        </div>
    </div>
    <br>
    <br>
    <div id="qn2">
        <h3 style="color:white;">2) Constructors are used to...</h3>
        <br>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-primary" style="color:white;">A:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">Build a user interface</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-success" style="color:white;">B:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">Free Memory</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-info" style="color:white;">C:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:greenyellow;">Initialize a newly created object</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-warning" style="color:white;">D:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">To create a subclass</p>
            </div>
        </div>

        <div class="chart-wrap vertical">      
            <div class="progress" style="height: 30px;">
                <div class="progress-bar" role="progressbar" style="width: <%= q2c1px %>%; height:100%;" aria-valuenow="<%= q2c1px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q2c1 %></h5></div>
                <div class="progress-bar bg-success" role="progressbar" style="width: <%= q2c2px %>%" aria-valuenow="<%= q2c2px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q2c2 %></h5></div>
                <div class="progress-bar bg-info" role="progressbar" style="width: <%= q2c3px %>%" aria-valuenow="<%= q2c3px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q2c3 %></h5></div>
                <div class="progress-bar bg-warning" role="progressbar" style="width: <%= q2c4px %>%" aria-valuenow="<%= q2c4px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q2c4 %></h5></div>
            </div>
        </div>
    </div>
    <br>
    <br>
    <div id="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3">
        <h3 style="color:white;">3) An object that has more than one form is referred to as:</h3>
        <br>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-primary" style="color:white;">A:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">Inheritance</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-success" style="color:white;">B:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">Interface</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-info" style="color:white;">C:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:white;">Abstract Class</p>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
                <button class="btn btn-warning" style="color:white;">D:</button> 
            </div>
            <div class="col-5 text-left">
                <p style="color:greenyellow;">Polymorphism</p>
            </div>
        </div>
        <div class="chart-wrap vertical">      
            <div class="progress" style="height: 30px;">
                <div class="progress-bar" role="progressbar" style="width: <%= q3c1px %>%; height:100%;" aria-valuenow="<%= q3c1px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q3c1 %></h5></div>
                <div class="progress-bar bg-success" role="progressbar" style="width: <%= q3c2px %>%" aria-valuenow="<%= q3c2px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q3c2 %></h5></div>
                <div class="progress-bar bg-info" role="progressbar" style="width: <%= q3c3px %>%" aria-valuenow="<%= q3c3px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q3c3 %></h5></div>
                <div class="progress-bar bg-warning" role="progressbar" style="width: <%= q3c4px %>%" aria-valuenow="<%= q3c4px %>" aria-valuemin="0" aria-valuemax="100"><h5><%= q3c4 %></h5></div>
            </div>
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
