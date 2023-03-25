import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.*; // Tomcat 10
import jakarta.servlet.http.*;
import java.net.HttpURLConnection;
import jakarta.servlet.annotation.*;

/*
 * This servlet handles the logging in process of the android app
 * This servlet communicates with the android app only
 */

@WebServlet("/login")
public class loginServlet extends HttpServlet {
      private static final Logger LOGGER = Logger.getLogger(loginServlet.class.getName());
      static String emailStr;
      static String passwordStr;
      static String userIDStr;

      @Override
      public void doGet(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {

            LOGGER.info("loginServlet called");
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            
            emailStr = request.getParameter("email");
            passwordStr = request.getParameter("password");

            try (Connection conn = DriverManager.getConnection(
                              "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                              "root", "password");
                  Statement stmt = conn.createStatement();) {

                  String sqlStr = "SELECT * FROM users WHERE email ='" + emailStr
                              + "' AND password ='" + passwordStr + "'";
                  LOGGER.info(sqlStr);
                  ResultSet rset = stmt.executeQuery(sqlStr);
                  
                  if (rset.next()) {
                        LOGGER.info("Login Successful");
                        userIDStr = rset.getString(1);
                        out.println(userIDStr);
                        
                  } else {
                        out.println("Wrong Email or Password");
                  }
                  
            } catch (Exception ex) {};
      }
}