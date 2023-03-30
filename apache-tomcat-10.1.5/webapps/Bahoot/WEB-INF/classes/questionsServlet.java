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

@WebServlet("/viewQuestions")
public class questionsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(questionsServlet.class.getName());
    int setid, qnNo, totalQn,questionID;
    String roomCode;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        LOGGER.info("MyServlet called");

        setid = Integer.parseInt(request.getParameter("setid"));
        totalQn = Integer.parseInt(request.getParameter("totalQn"));
        roomCode = request.getParameter("room_code");
        LOGGER.info("setId: " + setid);

        if(request.getParameter("qnNo") != null){
                qnNo = Integer.parseInt(request.getParameter("qnNo"));
        }else{
                qnNo = 1;
        }
        if(qnNo <= totalQn){
                try {
                        Connection conn = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                                "root", "password");

                        Statement stmt = conn.createStatement();
                        Statement stmt2 = conn.createStatement();
                        
                        String queryQuestions = "SELECT * FROM questions WHERE setID = " + setid + " AND qnNo = " + qnNo;
                        ResultSet rs = stmt.executeQuery(queryQuestions);
                        rs.next();

                        questionID = rs.getInt("id");
                        updateCurrentQn(stmt2, questionID);

                        response.setHeader("room_code",roomCode);
                        response.setIntHeader("setid", setid);
                        response.setIntHeader("qnNo",qnNo);
                        response.setIntHeader("totalQn", totalQn);
                        response.setHeader("question",rs.getString("question"));
                        response.setHeader("opt1", rs.getString("opt1"));
                        response.setHeader("opt2", rs.getString("opt2"));
                        response.setHeader("opt3", rs.getString("opt3"));
                        response.setHeader("opt4", rs.getString("opt4"));
                        RequestDispatcher rd = request.getRequestDispatcher("questions.jsp");
                        rd.forward(request,response);
                        
                } catch (Exception e) {
                        LOGGER.info("Something Failed" + e); // Add a logging statement
                }
        }
        else{
                
                RequestDispatcher rd = request.getRequestDispatcher("display");
                request.setAttribute("setID",Integer.toString(setid));
                request.setAttribute("room_code",roomCode);
                rd.forward(request,response);
        }
    }

    private static void updateCurrentQn(Statement stmt, int qnNo){
        try{
                String setQnNO = "Update session set current_question_id = " + qnNo;
                stmt.executeUpdate(setQnNO);
        }catch(Exception e){
                LOGGER.info("set qn NO failed: "+ e);
        }
    }
}

