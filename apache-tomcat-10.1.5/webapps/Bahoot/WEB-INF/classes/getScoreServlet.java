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

@WebServlet("/getScore")
public class getScoreServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(getScoreServlet.class.getName());

    String userID;
    String roomCode;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                LOGGER.info("IN getScore servlet");
                String userID = (String)request.getParameter("userID");
                String roomCode = (String)request.getParameter("room_code");

                response.setHeader("userID",userID);
                response.setHeader("room_code",roomCode);

                LOGGER.info(roomCode);
                

            try (
                Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");
                
                Statement getScoreSTMT = conn.createStatement();
                ) {

                String getScorequery = "SELECT score FROM score where userID=" +userID + " AND room_code =" +roomCode;
                ResultSet score = getScoreSTMT.executeQuery(getScorequery);
                score.next();
                
                response.setHeader("score", score.getString("score"));
                LOGGER.info("the score is " + score.getString("score"));
                String responseToClient = score.getString("score");
                response.getWriter().write(responseToClient);
                response.getWriter().flush();

                RequestDispatcher rd = request.getRequestDispatcher("");

                rd.forward(request,response);

            }catch(Exception e){
                LOGGER.info("Something failed: " + e);
            }
    }
}
  