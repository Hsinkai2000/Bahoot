import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/signupservlet")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class signupservlet extends HttpServlet {
	static String err ="";

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
		isValidInput(request);

   }

   private static bool isValidInput(HttpServletRequest request){
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        int mobile = request.getParameter("mobile");
        String password = request.getParameter("password");

        if(email==null){
			err += "Please enter your Email";
		}
		else if(name==null){
			err += "Please enter your name";
		}
		else if(mobile == 0){
			err += "Please enter your mobile number";
		}
		else if(password == null){
			err += "Please enter a password";
		}
		else if(password.length() < 8){
			err += "Pass word has to be more than 8 characters, alphanumeric and "
		}
   }
}