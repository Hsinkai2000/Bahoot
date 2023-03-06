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

@WebServlet("/checkout")
public class checkoutServlet extends HttpServlet {
    static String email;
    static String password;
    static String confirm;
    static String name;
    static int mobile;
    private static final Logger LOGGER = Logger.getLogger(signUpServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "password");

                Statement stmt = conn.createStatement();) {
            LOGGER.info("MyServlet called"); // Add a logging statement

            email = request.getParameter("email");
            name = request.getParameter("name");
            password = request.getParameter("password");
            confirm = request.getParameter("confirm");
            mobile = Integer.parseInt(request.getParameter("mobile"));
            String err = "";

            int count = verifyEmail(stmt);

            if (email == null || password == null || confirm == null) {
                err += "Please fill out all fields.\n";
            } else if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                err += "Please fill out all fields.\n";
            } else if (!password.equals(confirm)) {
                err += "Passwords do not match.\n";
            } else if (count != 0) {
                err += "Email is already in use.\n";
            }

            if (err != "") {

                LOGGER.info("error found: " + err); // Add a logging statement
                response.sendRedirect("http://localhost:9999/oldegg/signup.jsp?data=" + err);
            } else {
                // add to database and go to index.jsp

                LOGGER.info("Register"); // Add a logging statement
                registerToDb(response, stmt);
            }
        } catch (Exception e) {
            LOGGER.info("Something Failed" + e); // Add a logging statement
        }
    }

}
