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
 * This servlet executes SQL statements sent by the android app and returns the output
 * This servlet communicates with the android app only
 */

@WebServlet("/SQL")
public class SQLServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SQLServlet.class.getName());
    static String sqlStr;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("SQLServlet Called"); 

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        sqlStr = request.getParameter("sql");
        
        
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");
            Statement stmt = conn.createStatement();) {

            if (sqlStr.contains("SELECT") || sqlStr.contains("select")) {
            
                LOGGER.info("Executing " + sqlStr); // Add a logging statement
                ResultSet rset = stmt.executeQuery(sqlStr);
                ResultSetMetaData rsmd = rset.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                    
                // print the results of the SQL query
                while (rset.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rset.getString(i);
                        LOGGER.info(rsmd.getColumnName(i) + ":" + columnValue);
                        out.print(rsmd.getColumnName(i) + ":" +columnValue + " " /*+ rsmd.getColumnName(i)*/);
                        response.setHeader(rsmd.getColumnName(i), columnValue);

                    }
                }

                response.setHeader("SQL","Select");

            } else if (sqlStr.contains("UPDATE") || sqlStr.contains("update")){
                LOGGER.info("Executing " + sqlStr); // Add a logging statement
                stmt.executeUpdate(sqlStr);
                response.setHeader("SQL","Update");


            } else if (sqlStr.contains("INSERT") || sqlStr.contains("insert")){
                LOGGER.info("Executing " + sqlStr); // Add a logging statement
                stmt.executeUpdate(sqlStr);
                response.setHeader("SQL","Insert");
            }
            
            out.flush();
         
        } catch (SQLException e) {
            LOGGER.info("SQL Failed" + e); // Add a logging statement
        }
    }
}
