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
            
            LOGGER.info("Executing " + sqlStr); // Add a logging statement
            ResultSet rset = stmt.executeQuery(sqlStr);
            ResultSetMetaData rsmd = rset.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rset.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rset.getString(i);
                    LOGGER.info(rsmd.getColumnName(i) + ":" + columnValue);
                    out.print(rsmd.getColumnName(i) + ":" +columnValue + " " /*+ rsmd.getColumnName(i)*/);
                    
                }
                out.println(" ");
                
            }
            
        } catch (SQLException e) {

            LOGGER.info("SQL Failed" + e); // Add a logging statement
        }

    }

    

}
