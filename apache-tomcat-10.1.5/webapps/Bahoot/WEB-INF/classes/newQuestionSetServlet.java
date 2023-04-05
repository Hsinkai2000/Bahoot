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

@WebServlet("/NewQnSet")
public class newQuestionSetServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(newQuestionSetServlet.class.getName());
    private String qnSetName;
    private int totalQn;
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                LOGGER.info("IN newQnSet servlet");
                // String roomCode = (String)request.getParameter("room_code");

                // response.setHeader("room_code",roomCode);
                qnSetName = (String)request.getParameter("setName");
                totalQn = Integer.parseInt(request.getParameter("numQuestions"));


            try (
                Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");
                
                Statement createQnsSTMT = conn.createStatement();
                ) {

                String createqnSetquery = "INSERT INTO question_sets (name, totalQn, ownerID) VALUES ('"+qnSetName+"',"+totalQn+",1)";
                LOGGER.info("query 1 = " + createqnSetquery);

                PreparedStatement createqnSetSTMT = conn.prepareStatement(createqnSetquery,
                                      Statement.RETURN_GENERATED_KEYS);
                createqnSetSTMT.executeUpdate();
                LOGGER.info("query 1 = executed");
                try (ResultSet generatedKeys = createqnSetSTMT.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int key = generatedKeys.getInt(1);

                        for (int i = 1; i <= totalQn; i++) {
                            String qn= (String) request.getParameter("question"+i);
                            String opt1= (String)request.getParameter("option1_"+i);
                            String opt2= (String)request.getParameter("option2_"+i);
                            String opt3= (String)request.getParameter("option3_"+i);
                            String opt4= (String)request.getParameter("option4_"+i);
                            String correctOpt= (String)request.getParameter("correctOption"+i);
                            String queryAddQN = "INSERT INTO questions (setID, qnNo, question, opt1, opt2, opt3, opt4, correctOpt ) VALUES ('"+key+"','"+i+"','"+qn+"','"+opt1+"','"+opt2+"','"+opt3+"','"+opt4+"','"+correctOpt+"')";
                            LOGGER.info("query 2 = " + queryAddQN);
                            createQnsSTMT.executeUpdate(queryAddQN);
                            LOGGER.info("query 2 = executed");
                        }

                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                RequestDispatcher rd = request.getRequestDispatcher("");

                rd.forward(request,response);

            }catch(Exception e){
                LOGGER.info("Something failed: " + e);
            }
    }
}
  