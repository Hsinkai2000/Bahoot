// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
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
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/viewListing")  
public class listingServlet extends HttpServlet {
   private static final Logger LOGGER = Logger.getLogger(listingServlet.class.getName());
   static int id;

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
                
      id = Integer.parseInt(request.getParameter("listingId"));
      try (
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                                "root", "password"); 
            
        Statement stmt = conn.createStatement();
      ){
      
        Listing listing = obtainListing(stmt);
        ResultSet resultSet = getItemInfo(listing,stmt);
        if(resultSet.next()){
            createHeader(resultSet, response);
        }
        
        try{
            request.getRequestDispatcher("viewListing.jsp").forward(request, response);
        }catch(Exception e){
            LOGGER.info("unable to dispatch " + e); 
        }
      } catch (Exception e) {
        LOGGER.info("SQL Failed" + e);
      }
      
    
    }

    private static ResultSet getItemInfo(Listing listing, Statement stmt) {    
        //find listing item info
        try{
            //select coolers.* from coolers, listings where listings.id=4 and coolers.id=listings.itemID;
            String type = listing.type;
            String getListing = "SELECT " + type + ".* FROM " + type + ",listings WHERE listings.id=" + listing.id + " and " + type + ".id=" + listing.itemID;
            ResultSet itemResultSet  = stmt.executeQuery(getListing); 
    
            return itemResultSet;
        } catch (SQLException e) {
            
            LOGGER.info("SQL Failed" + e); // Add a logging statement
            return null;
        }
    }

    private static Listing obtainListing(Statement stmt) {        
        //check for listing
        try
        {
            String getListing = "SELECT * FROM Listings WHERE id=" + id;
            ResultSet listingResultSet = stmt.executeQuery(getListing); 
            Listing listing = new Listing();
    
            listingResultSet.next();
            listing = new Listing(listingResultSet.getInt(1),
            listingResultSet.getString(2),
            listingResultSet.getInt(3));
            LOGGER.info("Listing result =" + listing.itemID);
            return listing;
        } catch (SQLException e) {
            
            LOGGER.info("SQL Failed" + e); // Add a logging statement
            return null;
        }
    }

    private static String toString(ResultSet resultSet) {
        String finalString="";
        int i=2;
        try{
            final ResultSetMetaData meta = resultSet.getMetaData();
            final int columnCount = meta.getColumnCount();
            final List<String> columnList = new LinkedList<String>();

            for (int column = 2; column <= columnCount-1; ++column) 
            {
                final Object value = resultSet.getObject(column); 
                finalString+=String.valueOf(value) +"\n";
            }
        
        }catch(Exception e){
            LOGGER.info("resultset cannot next" + e); 
        }
        
        return finalString;
    }

    private static void createHeader(ResultSet resultSet, HttpServletResponse response) {
        try{

            LOGGER.info("resultSet resultID =" + resultSet.getInt(1));
            response.addHeader("name",resultSet.getString("name"));
            response.addHeader("price",String.format("%.2f", resultSet.getFloat("price")));
            response.addIntHeader("qty",resultSet.getInt("qty"));
            response.addHeader("link",resultSet.getString("link"));
            response.addHeader("itemInfo",toString(resultSet));
        }catch(Exception e){
            LOGGER.info("Failed to add header: " + e);
        }
    }
    
}