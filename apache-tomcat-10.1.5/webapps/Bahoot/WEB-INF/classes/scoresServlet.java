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
                String roomCode = (String)request.getAttribute("room_code");

                response.setHeader("setID",setID);
                response.setHeader("room_code",roomCode);

                LOGGER.info(roomCode);
                

            try (
                Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");
                
                Statement setIDstmt = conn.createStatement();
                Statement getRankingStmt = conn.createStatement();
                ) {

                String setIDquery = "SELECT * FROM question_sets where id='" +setID + "'";
                ResultSet siq = setIDstmt.executeQuery(setIDquery);

                String getRankingQuery = "SELECT * FROM score WHERE room_code = " + roomCode + " ORDER BY score DESC";
                ResultSet rankingRS = getRankingStmt.executeQuery(getRankingQuery);
                
                for(int i = 1; i<4; i++){
                    if(rankingRS.next()){
                        response.setHeader(String.format("name%d",i),rankingRS.getString("name"));
                        response.setHeader(String.format("score%d",i),rankingRS.getString("score"));
                    }                    
                }

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
  