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

    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                LOGGER.info("IN scores servlet");
            try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");

                Statement stmt1 = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                Statement stmt3 = conn.createStatement();) {

                String query1 = "Select choice, COUNT(*) AS count from responses where questionNo = 1 and choice != 0 group by choice";
                String query2 = "Select choice, COUNT(*) AS count from responses where questionNo = 2 and choice != 0 group by choice";
                String query3 = "Select choice, COUNT(*) AS count from responses where questionNo = 3 and choice != 0 group by choice"; 

                ResultSet rs1 = stmt1.executeQuery(query1);
                ResultSet rs2 = stmt2.executeQuery(query2);
                ResultSet rs3 = stmt3.executeQuery(query3);
                
                setHeader(response, rs1, 1);
                setHeader(response, rs2, 2);
                setHeader(response, rs3, 3);

                RequestDispatcher rd = request.getRequestDispatcher("stats.jsp");
                rd.forward(request,response);

            }catch(Exception e){
                LOGGER.info("Something failed: " + e);
            }
    }

    private static void setHeader(HttpServletResponse response, ResultSet rs, int qn){
        ArrayList<Integer> choice = new ArrayList<Integer>();
        ArrayList<Integer> count = new ArrayList<Integer>();
        try {
            while(rs.next()){
                choice.add(rs.getInt(1));
                count.add(rs.getInt(2));
            }
            LOGGER.info("choice list : " + choice);
            LOGGER.info("count list : " + count);

            if(!choice.contains(1)){
                choice.add(1);
                count.add(0);
            }
            if(!choice.contains(2)){
                choice.add(2);
                count.add(0);
            }
            if(!choice.contains(3)){
                choice.add(3);
                count.add(0);
            }
            if(!choice.contains(4)){
                choice.add(4);
                count.add(0);
            }

            for ( int i =0;i<4;i++){
                response.setIntHeader(String.format("q%dc%d",qn,choice.get(i)),count.get(i));
            }
        } catch (Exception e) {
            LOGGER.info("Exception caught: "+ e);
        }
    }

}
  