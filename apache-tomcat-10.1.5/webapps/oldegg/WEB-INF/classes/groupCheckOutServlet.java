import java.io.*;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.*; // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/groupCheckout")
public class groupCheckOutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(groupCheckOutServlet.class.getName());
    static String email;
    static String name;
    static String address;
    static String card_number;
    static int uid;
    static int listingID;
    static int itemID;
    static String itemType;
    static int qty;
    static ResultSet rsetCart;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        name = request.getParameter("name");
        email = request.getParameter("email");
        address = request.getParameter("address");
        card_number = request.getParameter("card_number");
        uid = Integer.parseInt(request.getParameter("uid"));
        

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "password");

                Statement stmt = conn.createStatement();
                Statement stmtReduceQTY = conn.createStatement();
                Statement stmtGetItems = conn.createStatement();
                Statement stmtCreateOrderItem = conn.createStatement();                
                Statement stmtCreateOrder = conn.createStatement();
                Statement stmtRemoveCart = conn.createStatement();) {

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
                response.sendRedirect("http://localhost:9999/oldegg/groupcheckout.jsp?data=" + err + "&uid="+uid);
            } else {
                //find cart
                getCart(stmt);
                List<String> typeList = new ArrayList<String>(); 
                List<Integer> listingIDList =  new ArrayList<Integer>(); 
                List<Integer> itemIDList = new ArrayList<Integer>(); 
                int count = 0;
                int i=0;
                while (rsetCart.next()){                
                    typeList.add(rsetCart.getString("type"));
                    listingIDList.add(rsetCart.getInt("id"));
                    itemIDList.add(rsetCart.getInt("itemID"));
                    count++;
                }

                LOGGER.info("count : "+ count);

                // create order
                PrintWriter out = response.getWriter();
                String createOrder = "Insert into Orders (userID) Values (" + uid + ")";
                int numRowsAffected = stmtCreateOrder.executeUpdate(createOrder, Statement.RETURN_GENERATED_KEYS);
                // find orderid
                int orderID = 0;
                if (numRowsAffected == 1) {
                    ResultSet rs = stmtCreateOrder.getGeneratedKeys();

                    if (rs.next()) {
                        orderID = rs.getInt(1);
                        while(i<count){
                            String getitems = "SELECT " + typeList.get(i) + ".* FROM " + typeList.get(i) + ",listings WHERE listings.id=" + listingIDList.get(i) + " and " + typeList.get(i) + ".id=" + itemIDList.get(i);
                            ResultSet itemset = stmtGetItems.executeQuery(getitems);

                            LOGGER.info("getitems called");
                            while(itemset.next()){
                                String link = itemset.getString("link");
                                String name = itemset.getString("name");
                                Float price = itemset.getFloat("price");
                                int qty = itemset.getInt("qty");
                                

                                String createOrderItem = "Insert into OrderItems (orderID, listingID) values (" + orderID + ","
                                        + listingIDList.get(i) + ")";
                                stmtCreateOrderItem.executeUpdate(createOrderItem);

                                LOGGER.info("createOrderItem called");
                                // reduce qty
                                String reduceQTY = "UPDATE " + typeList.get(i) + " SET qty =" + (qty - 1) + " WHERE id =" + itemIDList.get(i);
                                stmtReduceQTY.executeUpdate(reduceQTY);

                            }
                            i++;
                        }                      
                    }

                    //remove from cart
                    String removeCart = "DELETE FROM carts WHERE userID=" +uid;
                    stmtRemoveCart.executeUpdate(removeCart);
                    
                    LOGGER.info("reduceQTY called");
                    response.setContentType("text/html");
                    out.println("<html><body>");
                    out.println(
                            "<p>Congratulations on your purchase! please wait patiently for your item to arrive!</p>");
                    
                    out.println("<button onclick=\"location.href='index.jsp?uid=" + uid + "'\">Go back to index</button>");
                    
                    out.println("</body></html>");
                    LOGGER.info("purchased"); // Add a logging statement
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

    private void getCart(Statement stmt) {
        try{
            String sqlStr = "select listings.* from listings,carts where listings.id = carts.listingID AND carts.userID = " + uid;

            LOGGER.info("getCart called");
            rsetCart = stmt.executeQuery(sqlStr);
        }catch(Exception e){
            LOGGER.info("finding cart failed: " + e);
        }
    }

}
