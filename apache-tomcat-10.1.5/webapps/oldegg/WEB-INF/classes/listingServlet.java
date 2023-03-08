
import java.io.*;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

import javax.xml.validation.Validator;

import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/viewListing")
public class listingServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(listingServlet.class.getName());
    static int listingId;
    static int userID;
    static int itemID;
    static float price;
    static int qty;
    static String type;
    static String name, link;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getParameter("action");
        if (request.getParameter("uid") != null) {
            userID = Integer.parseInt(request.getParameter("uid"));
            LOGGER.info("userid: " + userID);
            try (
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                            "root", "password");

                    Statement stmt = conn.createStatement();) {

                LOGGER.info("POST");
                if (action.equals("purchase")) {
                    LOGGER.info("Make Purchase");
                    getProductNamePrice(request);
                    purchaseItem(request, response, stmt);
                } else if (action.equals("addtocart")) {
                    LOGGER.info("Add to cart");
                    addtocart(request, response, stmt);
                }
            } catch (Exception e) {
                LOGGER.info("SQL Connection error: " + e);
            }
            response.getHeader("");
        } else {
            listingId = Integer.parseInt(request.getParameter("listingId"));
            response.sendRedirect("http://localhost:9999/oldegg/login.jsp?listingId=" + listingId);
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("uid") != null) {
            userID = Integer.parseInt(request.getParameter("uid"));
            LOGGER.info("userid: " + userID);
        }
        listingId = Integer.parseInt(request.getParameter("listingId"));
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "password");

                Statement stmt = conn.createStatement();) {

            Listing listing = obtainListing(stmt);
            ResultSet resultSet = getItemInfo(listing, stmt);
            if (resultSet.next()) {
                createHeader(resultSet, response, listing);
            }

            try {
                request.getRequestDispatcher("viewListing.jsp").forward(request, response);
            } catch (Exception e) {
                LOGGER.info("unable to dispatch " + e);
            }
        } catch (Exception e) {
            LOGGER.info("SQL Failed" + e);
        }

    }

    private static ResultSet getItemInfo(Listing listing, Statement stmt) {
        // find listing item info
        try {
            // select coolers.* from coolers, listings where listings.id=4 and
            // coolers.id=listings.itemID;
            type = listing.type;
            String getListing = "SELECT " + type + ".* FROM " + type + ",listings WHERE listings.id=" + listing.id
                    + " and " + type + ".id=" + listing.itemID;
            ResultSet itemResultSet = stmt.executeQuery(getListing);

            return itemResultSet;
        } catch (SQLException e) {

            LOGGER.info("SQL Failed" + e); // Add a logging statement
            return null;
        }
    }

    private static Listing obtainListing(Statement stmt) {
        // check for listing
        try {
            String getListing = "SELECT * FROM Listings WHERE id=" + listingId;
            ResultSet listingResultSet = stmt.executeQuery(getListing);
            Listing listing = new Listing();

            listingResultSet.next();
            listing = new Listing(listingResultSet.getInt(1),
                    listingResultSet.getString(2),
                    listingResultSet.getInt(3));
            itemID = listing.itemID;
            LOGGER.info("Listing result =" + listing.itemID);
            return listing;
        } catch (SQLException e) {

            LOGGER.info("SQL Failed" + e); // Add a logging statement
            return null;
        }
    }

    private static String toString(ResultSet resultSet) {
        String finalString = "";
        int i = 2;
        try {
            final ResultSetMetaData meta = resultSet.getMetaData();
            final int columnCount = meta.getColumnCount();
            final List<String> columnList = new LinkedList<String>();

            for (int column = 2; column <= columnCount - 1; ++column) {
                final Object value = resultSet.getObject(column);
                finalString += String.valueOf(value) + "\n";
            }

        } catch (Exception e) {
            LOGGER.info("resultset cannot next" + e);
        }

        return finalString;
    }

    private static void createHeader(ResultSet resultSet, HttpServletResponse response, Listing listing) {
        try {
            response.addHeader("type", listing.type);
            LOGGER.info("listing type = " + listing.type);
            response.addIntHeader("listingId", listing.id);
            response.addIntHeader("uid", userID);
            response.addIntHeader("itemID", itemID);
            response.addHeader("name", resultSet.getString("name"));
            response.addHeader("price", String.format("%.2f", resultSet.getFloat("price")));
            response.addIntHeader("qty", resultSet.getInt("qty"));
            response.addHeader("link", resultSet.getString("link"));
            response.addHeader("itemInfo", toString(resultSet));
        } catch (Exception e) {
            LOGGER.info("Failed to add header: " + e);
        }
    }

    private void addtocart(HttpServletRequest request, HttpServletResponse response, Statement stmt)
            throws ServletException, IOException {
        try {
            LOGGER.info("In function");
            String addCart = "INSERT INTO Carts (listingID,userID) VALUES (" + listingId + "," + userID + ")";
            stmt.executeUpdate(addCart);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<p>Product has been added to cart!</p>");
            out.println("<button onclick=\"location.href='index.jsp?uid=" + userID + "'\">Go back to index</button>");
            out.println("</body></html>");
            LOGGER.info("Added to cart!");
        } catch (Exception e) {
            LOGGER.info("Failed to insert to cart" + e);
        }
    }

    private void purchaseItem(HttpServletRequest request, HttpServletResponse response, Statement stmt)
            throws ServletException, IOException {
        try {
            LOGGER.info("In puchase function");
            // go to checkout page
            User user = getUserEmailName(stmt);
            if (user.email != null) {

                response.addIntHeader("uid", user.id);
                response.addHeader("email", user.email);
                response.addHeader("name", user.name);
                response.addIntHeader("itemID", itemID);
                response.addHeader("price", String.format("%.2f", price));
                response.addIntHeader("qty", qty);
                response.addHeader("link", link);
                response.addHeader("itemName", name);
                response.addIntHeader("listingId", listingId);
                response.addHeader("itemType", type);
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
            }
            // response.sendRedirect("http://localhost:9999/oldegg/checkout.jsp?uid=" +
            // userID);
        } catch (Exception e) {
            LOGGER.info("Failed to purchase" + e);
        }
    }

    private User getUserEmailName(Statement stmt) {
        try {
            LOGGER.info("In getUserEmailName function");
            // get user email and name
            String getUser = "SELECT id,email,name FROM users WHERE id=" + userID;
            ResultSet userResultSet = stmt.executeQuery(getUser);
            userResultSet.next();
            User user = new User();
            user.id = userResultSet.getInt("id");
            user.email = userResultSet.getString("email");
            user.name = userResultSet.getString("name");
            return user;
        } catch (Exception e) {
            LOGGER.info("Failed to insert to cart" + e);
        }
        return new User();
    }

    private void getProductNamePrice(HttpServletRequest request) {
        type = request.getParameter("itemType");
        itemID = Integer.parseInt(request.getParameter("itemID"));
        price = Float.parseFloat(request.getParameter("price"));
        qty = Integer.parseInt(request.getParameter("qty"));
        link = request.getParameter("link");
        name = request.getParameter("name");
    }

}