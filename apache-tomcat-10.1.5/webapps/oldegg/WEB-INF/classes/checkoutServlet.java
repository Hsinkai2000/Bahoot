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
    private static final Logger LOGGER = Logger.getLogger(checkoutServlet.class.getName());
    static String email;
    static String name;
    static String address;
    static String card_number;
    static int uid;
    static int listingID;
    static int itemID;
    static String itemType;
    static int qty;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
                        String reduceQTY = "UPDATE " + itemType + " SET qty =" + (qty - 1) + " WHERE id =" + itemID;
                        stmt.executeUpdate(reduceQTY);

                        response.setContentType("text/html");
                        out.println("<html><body>");
                        out.println(
                                "<p>Congratulations on your purchase! please wait patiently for your item to arrive!</p>");
                        out.println("<button onclick=\"location.href='index.jsp?uid=" + uid + "'\">Go back to index</button>");
                        out.println("</body></html>");
                        LOGGER.info("purchased"); // Add a logging statement
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
