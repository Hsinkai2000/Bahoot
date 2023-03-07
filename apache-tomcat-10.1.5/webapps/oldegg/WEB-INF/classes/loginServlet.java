
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
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
      private static final Logger LOGGER = Logger.getLogger(loginServlet.class.getName());

      int listingId = 0;

      @Override
      public void doGet(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {
            try (
                        Connection conn = DriverManager.getConnection(
                                    "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                                    "root", "password");
                        Statement stmt = conn.createStatement();) {
                  String sqlStr = "SELECT * FROM users WHERE email ='" + request.getParameter("email")
                              + "' AND password ='" + request.getParameter("password") + "'";
                  ResultSet rset = stmt.executeQuery(sqlStr);
                  int count = 0;
                  int id = 0;
                  if (request.getParameter("listingId") != null) {
                        listingId = Integer.parseInt(request.getParameter("listingId"));
                  }
                  while (rset.next()) {
                        count++;
                        id = rset.getInt(1);
                  }
                  if (count == 0) {
                        Object data = "Wrong Email or Password";
                        response.sendRedirect("http://localhost:9999/oldegg/login.jsp?data=" + data);
                  } else {
                        LOGGER.info("MyServlet called");
                        if (listingId != 0) {
                              // response.sendRedirect("http://localhost:9999/oldegg/viewListing.jsp?uid=" + id + "&listingId=" + listingId);
                              
                              String queryString = "?uid=" + id  + "&listingId=" + listingId;
                              
                              RequestDispatcher dispatcher = request.getRequestDispatcher("viewListing" + queryString);
                              dispatcher.forward(request, response);
                        } else {
                              response.sendRedirect("http://localhost:9999/oldegg/index.jsp?uid=" + id);
                        }
                  }
            } catch (Exception ex) {
            }
            ;
      }

}