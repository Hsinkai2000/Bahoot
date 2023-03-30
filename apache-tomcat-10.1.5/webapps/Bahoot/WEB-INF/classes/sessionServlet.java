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

@WebServlet("/endSession")
public class sessionServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(sessionServlet.class.getName());

    String setID;
    String roomCode;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                LOGGER.info("IN sessions servlet");
                String roomCode = request.getParameter("room_code");

                LOGGER.info(roomCode);
                

            try (
                Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");
                
                Statement stmt = conn.createStatement();
                ) {

                String deleteSessionQuery = "DELETE FROM session WHERE room_code='" + roomCode + "'";
                stmt.executeUpdate(deleteSessionQuery);
                LOGGER.info("deleted Session");

                String deleteSessionResponses = "DELETE FROM responses WHERE room_code='" + roomCode + "'";
                stmt.executeUpdate(deleteSessionResponses);
                LOGGER.info("deleted resopnses");

                String deleteSessionScore = "DELETE FROM score WHERE room_code='" + roomCode + "'";
                stmt.executeUpdate(deleteSessionScore);
                LOGGER.info("deleted Session");

                
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");

                rd.forward(request,response);

            }catch(Exception e){
                LOGGER.info("Something failed: " + e);
            }
    }
}
  