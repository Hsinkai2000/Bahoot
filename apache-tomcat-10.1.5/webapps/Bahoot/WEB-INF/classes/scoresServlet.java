import java.io.*;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.logging.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.*; // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.ArrayList;

/*
 * This servlet handles the backend responses sent by students 
 * This servlet communicates with the android app only
 */

@WebServlet("/display")
public class scoresServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(scoresServlet.class.getName());

    String setID;
    String roomCode;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                LOGGER.info("IN scores servlet");
                LOGGER.info("setID: " + request.getParameter("setID"));

                String setID = (String)request.getAttribute("setID");
                String roomCode = (String)request.getAttribute("roomCode");

                response.setHeader("setID",setID);
                response.setHeader("roomCode",roomCode);

                LOGGER.info(roomCode);
                

            try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");

                
                Statement setIDstmt = conn.createStatement();) {

                String setIDquery = "SELECT * FROM qnsets where id='" +setID + "'";
                ResultSet siq = setIDstmt.executeQuery(setIDquery);

                siq.next();

                String setName = siq.getString("name");
                response.setHeader("setName", setName);
                
                RequestDispatcher rd = request.getRequestDispatcher("stats.jsp");

                rd.forward(request,response);

            }catch(Exception e){
                LOGGER.info("Something failed: " + e);
            }
    }
}
  