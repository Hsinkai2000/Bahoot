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
      static String nameStr;

      @Override
      public void doGet(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {

            LOGGER.info("loginServlet called");
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
  
            emailStr = request.getParameter("email");
            passwordStr = request.getParameter("password");

            try (Connection conn = DriverManager.getConnection(
                              "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                              "root", "password");
                  ) {

                  String sqlStr = "SELECT * FROM users WHERE email = ? AND password = ?";
                  PreparedStatement pstmt = conn.prepareStatement(sqlStr);
                  pstmt.setString(1, emailStr);
                  pstmt.setString(2, passwordStr);
                  ResultSet rset = pstmt.executeQuery();
                  
                  if (rset.next()) {
                        LOGGER.info("Login Successful");
                        userIDStr = rset.getString(1);
                        nameStr = rset.getString(3);
                        response.setHeader("Login","Success");
                        response.setHeader("userID",userIDStr);
                        response.setHeader("name",nameStr);
                  
                        
                  } else {
                        response.setHeader("Login","Failure");

                  }

                 out.flush();
                  
            } catch (Exception ex) {};
      }
}