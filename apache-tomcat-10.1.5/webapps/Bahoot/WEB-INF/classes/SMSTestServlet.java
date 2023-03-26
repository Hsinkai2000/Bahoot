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

@WebServlet("/sms")
public class SMSTestServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SMSTestServlet.class.getName());
    static String sqlStr;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("SMSTestServlet Called"); 

    }
}
