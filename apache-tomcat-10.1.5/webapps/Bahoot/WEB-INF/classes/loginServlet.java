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


@WebServlet("/login")
public class usersServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(loginServlet.class.getName());
    
     public void doGet(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {
            try (
                        Connection conn = DriverManager.getConnection(
                                    "jdbc:mysql://localhost:3306/Bahoot?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                                    "root", "password");
                        Statement stmt = conn.createStatement();) {
                  String sqlStr = "SELECT * FROM users WHERE email ='" + request.getParameter("email")
                              + "' AND password ='" + request.getParameter("password") + "'";
                  ResultSet rset = stmt.executeQuery(sqlStr);
                  int count = 0;
                  int id = 0;
                  
                  while (rset.next()) {
                        count++;
                        id = rset.getInt(1);
                  }
                  if (count == 0) {
                        Object data = "Wrong Email or Password";
                        response.sendRedirect("http://localhost:9999/oldegg/login.jsp?data=" + data);
                  } else {
                        LOGGER.info("MyServlet called");
                        if (request.getParameter("listingId") != null) {
                              // response.sendRedirect("http://localhost:9999/oldegg/viewListing.jsp?uid=" + id + "&listingId=" + listingId);
                              
                              String queryString = "?uid=" + id  + "&listingId=" + request.getParameter("listingId");
                              
                              RequestDispatcher dispatcher = request.getRequestDispatcher("viewListing" + queryString);
                              dispatcher.forward(request, response);
                        } else {
                              response.sendRedirect("http://localhost:9999/oldegg/index.jsp?uid=" + id);
                        }
                  }
            } catch (Exception ex) {
            }
            ;
      }

            throws ServletException, IOException {
        name = request.getParameter("name");
        email = request.getParameter("email");
        address = request.getParameter("address");
        card_number = request.getParameter("card_number");
        uid = Integer.parseInt(request.getParameter("uid"));
        listingID = Integer.parseInt(request.getParameter("listingId"));
        itemID = Integer.parseInt(request.getParameter("itemID"));
        itemType = request.getParameter("itemType");
        qty = Integer.parseInt(request.getParameter("qty"));
        //qtyBought = 1;
        qtyBought = Integer.parseInt(request.getParameter("quantityBought"));

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "password");

                Statement stmt = conn.createStatement();) {
            LOGGER.info("MyServlet called");

            // Validate the form data
            String err = "";
            if (name == null || name.trim().equals("")) {
                err += "Please enter your real name\n";
            }
            if (email == null || email.trim().equals("")) {
                err += "Please enter a valid email address\n";
            }
            if (address == null || address.trim().equals("")) {
                err += "Please enter a valid address\n";
            }
            if (card_number == null || card_number.trim().equals("")) {
                err += "Please enter a card number\n";
            }
            if (err != "") {

                LOGGER.info("error found: " + err); // Add a logging statement
                response.sendRedirect("http://localhost:9999/oldegg/checkout.jsp?data=" + err);
            } else {
                // create order
                PrintWriter out = response.getWriter();
                String createOrder = "Insert into Orders (userID) Values (" + uid + ")";
                int numRowsAffected = stmt.executeUpdate(createOrder, Statement.RETURN_GENERATED_KEYS);
                // find orderid
                int orderID = 0;
                if (numRowsAffected == 1) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        orderID = rs.getInt(1);
                        String createOrderItem = "Insert into OrderItems (orderID, listingID) values (" + orderID + ","
                                + listingID + ")";
                        stmt.executeUpdate(createOrderItem);
                        // reduce qty
                        LOGGER.info("Item TYPE!!!!!!= "+ itemType);
                        String reduceQTY = "UPDATE " + itemType + " SET qty =" + (qty - qtyBought) + " WHERE id =" + itemID;
                        stmt.executeUpdate(reduceQTY);

                        response.sendRedirect("http://localhost:9999/oldegg/index.jsp?uid=" + uid +"&purchased=yes");

                        response.setContentType("text/html");
                        out.println("<html><body>");
                        out.println(
                                "<p>Congratulations on your purchase! please wait patiently for your item to arrive!</p>");
                        out.println("<button onclick=\"location.href='index.jsp?uid=" + uid + "'\">Go back to index</button>");
                        out.println("</body></html>");
                        LOGGER.info("purrchased"); // Add a logging statement
                    }
                }
                else{
                    out.println("<html><body>");
                    out.println(
                            "<p>Sorry something went wrong. please try again.</p>");
                    out.println("<button onclick=\"location.href='index.jsp?uid=" + uid + "'\">Go back to index</button>");
                    out.println("</body></html>");
                    LOGGER.info("failure to purchase"); // Add a logging statement
                }

            }
        } catch (Exception e) {
            LOGGER.info("Something Failed" + e); // Add a logging statement
        }
    }

}
