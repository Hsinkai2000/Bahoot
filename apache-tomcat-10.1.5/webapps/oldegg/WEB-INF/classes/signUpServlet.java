
import java.io.*;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.logging.*;
import java.sql.*;
import jakarta.servlet.*; 
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/signup") 
public class signUpServlet extends HttpServlet {
    static String email;
    static String password;
    static String confirm;
    static String name;
    static int mobile;
    private static final Logger LOGGER = Logger.getLogger(signUpServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("MyServlet called"); // Add a logging statement
			
        email = request.getParameter("email");
        name = request.getParameter("name");
        password = request.getParameter("password");
        confirm = request.getParameter("confirm");
	    mobile = Integer.parseInt(request.getParameter("mobile")) ;
		String err="";

        int count = verifyEmail();


        if (email == null || password == null || confirm == null) {
            err += "Please fill out all fields.\n";
        } else if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            err += "Please fill out all fields.\n";
        } else if (!password.equals(confirm)) {
            err += "Passwords do not match.\n";
        } else if (count != 0) {
            err += "Email is already in use.\n";
        } 
        
        if(err!=""){

            LOGGER.info("error found: " + err); // Add a logging statement
            response.sendRedirect("http://localhost:9999/oldegg/signup.jsp?data="+err);
        }
        else {
            //add to database and go to index.jsp

            LOGGER.info("Register"); // Add a logging statement
            registerToDb(response);
        }
    }

    private static void registerToDb(HttpServletResponse response ) {
        //register user
        try(
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                                "root", "rootpass"); 
            
            Statement stmt = conn.createStatement();
        ){

            String sqlStrRegister = "INSERT INTO Users Values (null, '" + email + "', '"+ name + "', '" + password + "', '" + mobile + "')";
            stmt.executeUpdate(sqlStrRegister);  
            int id = verifyEmail();        
            response.setIntHeader("id", id);
            try{
                response.sendRedirect("http://localhost:9999/oldegg/index.jsp");
            }catch(Exception e){
                LOGGER.info("Redirect Failed" + e); // Add a logging statement

            }
        }
        catch(SQLException e){

            LOGGER.info("SQL Failed" + e); // Add a logging statement
        }

		
    }

    private static int verifyEmail() {        
        //check if email exists
        try(
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                                "root", "rootpass"); 
            
            Statement stmt = conn.createStatement();
        )
        {
            int id=0;
            String sqlStrEmail = "SELECT * FROM users WHERE email ='" + email + "'";
            ResultSet rsetEmail = stmt.executeQuery(sqlStrEmail); 
    
            while(rsetEmail.next()){
                id = rsetEmail.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            
            LOGGER.info("SQL Failed" + e); // Add a logging statement
        }
       return 0;
    }
}
