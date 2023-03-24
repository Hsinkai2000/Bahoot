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


@WebServlet("/response")
public class responseServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(responseServlet.class.getName());
    static String option;
    static String correctOption;
    static String userID;
    static String currentQuestion;
    static String respondee;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("responseServlet Called"); 

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        option = request.getParameter("option");
        userID = request.getParameter("userID");
        
        
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");

            Statement stmt = conn.createStatement();) {
                
            String sqlStr = "SELECT correctOPT FROM questions WHERE current='Y'";
            
            LOGGER.info(sqlStr); // Add a logging statement
            ResultSet rset = stmt.executeQuery(sqlStr);
            rset.next();
            correctOption = rset.getString(1);
            LOGGER.info("Correct Option: " + correctOption);

            if (option.matches(correctOption))
                out.print("Correct");
            else
                out.print("Incorrect");

            sqlStr = "SELECT id FROM questions WHERE current ='Y'";
            rset = stmt.executeQuery(sqlStr);
            rset.next();
            currentQuestion = rset.getString(1);
            LOGGER.info("Current Question: " + currentQuestion);

            sqlStr = "SELECT name FROM users WHERE id ='" + userID + "'";
            rset = stmt.executeQuery(sqlStr);
            rset.next();
            respondee = rset.getString(1);
            LOGGER.info("Current Respondee: " + respondee);

            sqlStr = "INSERT INTO responses Values (null, '" + currentQuestion + "', '" + option + "', '" + respondee +"')";
            LOGGER.info(sqlStr); // Add a logging statement
            stmt.executeUpdate(sqlStr);
                
            
        } catch (SQLException e) {

            LOGGER.info("SQL Failed" + e); // Add a logging statement
        }

    }

    

}
