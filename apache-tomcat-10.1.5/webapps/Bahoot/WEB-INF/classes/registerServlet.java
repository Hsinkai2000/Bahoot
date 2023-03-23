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


@WebServlet("/register")
public class registerServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(registerServlet.class.getName());
    static String name;
    static String email;
    static String password;
    static String phoneNumber;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("MyServlet called"); // Add a logging statement

        name = request.getParameter("name");
        email = request.getParameter("email");
        password = request.getParameter("password");
        phoneNumber = request.getParameter("phoneNumber");
        String err = "";

        if (verifyEmail() == false)
            err = "wfef";
        else
            registerToDb(response);

       
    }

    private static void registerToDb(HttpServletResponse response) {
        // register user
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "password");

                Statement stmt = conn.createStatement();) {

            String sqlStrRegister = "INSERT INTO Users Values (null, '" + email + "', '" + name + "', '" + password
                    + "', '" + phoneNumber + "')";
            stmt.executeUpdate(sqlStrRegister);
            
        } catch (SQLException e) {

            LOGGER.info("SQL Failed" + e); // Add a logging statement
        }

    }

    private static boolean verifyEmail() {
        // check if email exists
        try (
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "password");

            Statement stmt = conn.createStatement();) {
                int id = 0;
                String sqlStrEmail = "SELECT * FROM users WHERE email ='" + email + "'";
                ResultSet rsetEmail = stmt.executeQuery(sqlStrEmail);

                if (rsetEmail.next() == false)
                    return false;
                else 
                    return true;
            } catch (SQLException e) {

                LOGGER.info("SQL Failed" + e); // Add a logging statement
            }
        return false;
    }
    

        

}
