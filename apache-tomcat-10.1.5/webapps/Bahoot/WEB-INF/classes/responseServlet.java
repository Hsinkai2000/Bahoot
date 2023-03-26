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

/*
 * This servlet handles the backend responses sent by students 
 * This servlet communicates with the android app only
 */

@WebServlet("/response")
public class responseServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(responseServlet.class.getName());
    static String option;
    static String correctOption;
    static String userID;
    static String roomCode;
    static String currentQuestionID;
    static String respondee;
    static String userComment;
    static String responseTableID;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("responseServlet Called"); 

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        option = request.getParameter("option");
        userID = request.getParameter("userID");
        roomCode = request.getParameter("roomCode");
        userComment = request.getParameter("userComment");


        
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");

            Statement stmt = conn.createStatement();) {

            // get current questionID
            String sqlStr = "SELECT currentQuestionID FROM session WHERE roomCode ='" + roomCode +"'";
            ResultSet rset = stmt.executeQuery(sqlStr);
            rset.next();
            currentQuestionID = rset.getString(1);
            LOGGER.info("Current QuestionID: " + currentQuestionID);

            // get correct option
            sqlStr = "SELECT correctOPT FROM questions WHERE id='" + currentQuestionID + "'";
            LOGGER.info(sqlStr); // Add a logging statement
            rset = stmt.executeQuery(sqlStr);
            rset.next();
            correctOption = rset.getString(1);
            LOGGER.info("Correct Option: " + correctOption);
            
            // send the result
            if (option.matches(correctOption))
                response.setHeader("Result","Correct");//out.print("Question Result:" + correctOption);
            else
                response.setHeader("Result","Incorrect");//out.print("Question Result:Incorrect");
            
            // get respondee name
            sqlStr = "SELECT name FROM users WHERE id ='" + userID + "'";
            LOGGER.info("Executing " + sqlStr);
            rset = stmt.executeQuery(sqlStr);
            rset.next();
            respondee = rset.getString(1);
            LOGGER.info("Current Respondee: " + respondee);

            // send statistic to DB
            sqlStr = "INSERT INTO responses (questionNo, choice, respondee, userID, comment ) Values "
                + "('" + currentQuestionID + "', '" + option + "', '" + respondee +"', '" + userID + "', '" + userComment +"')";
            LOGGER.info("Executing " + sqlStr); // Add a logging statement
            stmt.executeUpdate(sqlStr);

            // get latest response Table ID 
            sqlStr = "SELECT id FROM responses ORDER BY id DESC" ;
            LOGGER.info("Executing " + sqlStr); // Add a logging statement
            rset = stmt.executeQuery(sqlStr);
            rset.next();
            responseTableID = rset.getString(1);
            LOGGER.info("Responses Table ID:" + responseTableID);
            response.setHeader("Response-Table-ID",responseTableID);

                
        } catch (SQLException e) {
            LOGGER.info("SQL Failed" + e); // Add a logging statement
        }
    }
}
