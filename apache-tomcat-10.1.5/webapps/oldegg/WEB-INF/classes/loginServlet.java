// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.logging.*;
import java.sql.*;
import jakarta.servlet.*; 
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/login")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class loginServlet extends HttpServlet {
   private static final Logger LOGGER = Logger.getLogger(loginServlet.class.getName());
   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      ServletOutputStream out = response.getOutputStream();
      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                           "root", "password");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
            String sqlStr = "SELECT * FROM users WHERE email ='" + request.getParameter("email")
                     + "' AND password ='"+ request.getParameter("password") + "'";
            ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server
            int count=0;
            int id=0;
            while(rset.next()){
                  count++;
                  id = rset.getInt(1);
            }
            if(count == 0){
                  Object data = "Wrong Email or Password";
                  response.sendRedirect("http://localhost:9999/oldegg/login.jsp?data="+data);
            }
            else{
                  LOGGER.info("MyServlet called"); // Add a logging statement
                  response.setIntHeader("id", id);
                  try{
                        response.sendRedirect("http://localhost:9999/oldegg/index.jsp");
                    }catch(Exception e){
                        
                    }
            }
        }catch(Exception ex) {
         };
      //    chockhong@gmail.com
      //    passwordlim
    }
    
}