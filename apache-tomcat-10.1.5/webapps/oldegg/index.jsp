<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ page import ="java.io.*,java.util.*,java.sql.*,java.text.*"%> 
<% DecimalFormat priceFormatter= new DecimalFormat("$#0.00"); Connection conn = DriverManager.getConnection(
"jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
"root", "password"); Statement stmt = conn.createStatement(); String sqlStr; %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Old Egg | Home</title>
    <link rel="icon" type="image/x-icon" href="./images/oldegg-icon.png" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
      crossorigin="anonymous"
    />
    <link href="./style/style.css" rel="stylesheet" />
    <style>
      .carousel {
        overflow-x: scroll;
        overflow-y: hidden;
        white-space: nowrap;
    }
    .carousel-inner {
        display: inline-block;
    }
    .carousel-item {
        display: inline-block;
        width: 300px; /* adjust this as needed */
        margin-right: 10px; /* adjust this as needed */
    }
    </style>
  </head>
  <body class="bg_default">
    <nav
      class="navbar sticky-top navbar-expand-lg navbar-light bg_default"
      style="padding: 20px 50px 15px 50px"
    >
      <a
        class="navbar-brand"
        href="index.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
        style="padding-bottom: 15px; padding-right: 50px"
        
      >
        <img
          src="./images/oldegg-logo-transparent.png"
          width="150dp"
          height="50dp"
          alt="OldEgg"
        />
      </a>
      <form method="get" action="search.jsp" class="navbar-form" role="search">
        <div class="input-group" style="width: 40em">
          <input
            type="text"
            class="form-control pl-5"
            placeholder="Search parts"
            name="srch-term"
            id="srch-term-header"
          />
          <% if(request.getParameter("uid") != null) { %>
            <input type="hidden" name="uid" value="<%=request.getParameter("uid")%>">
          <% } %>
          <div class="input-group-btn">
            <button
              class="btn bg_orange"
              width="150dp"
              height="50dp"
              type="submit"
            >
              <img src="./images/btn-search.svg" alt="Wishlist" height="30dp" />
            </button>
          </div>
        </div>
      </form>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNavAltMarkup"
        aria-controls="navbarNavAltMarkup"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div
        class="collapse navbar-collapse fixed-right justify-content-end"
        id="navbarNavAltMarkup"
      >
        <div class="navbar-nav">
          <%
          if(request.getParameter("uid") != null) {
          %>
              <a class="nav-item nav-link" href="cart.jsp?uid=<%=request.getParameter("uid")%>"><img src="./images/btn-cart.svg" alt="Wishlist" height="30dp"/> Cart</a>
          <%
            }%>
          <%
          if(request.getParameter("uid") != null) {
          %>
              <a class="nav-item nav-link" href="signout.jsp?uid=<%=request.getParameter("uid")%>"><img src="./images/btn-account.svg" alt="Wishlist" height="30dp"
                />Sign out</a>
          <%
            } else {
          %>
              <a class="nav-item nav-link" href="login.jsp"><img src="./images/btn-account.svg" alt="Wishlist" height="30dp"
                />Log In</a>
          <%
            }
          %>
        </div>
      </div>
    </nav>

    <div style="padding-left: 50px; padding-right: 50px">
      <span class="inline" style="color: #7541b0">SHOP CATEGORIES:</span>
      <nav class="nav nav-pills flex-column flex-sm-row inline pb-5 pt-1">
        <a class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
           href="gpu.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
         >GPUs</a>

        <a class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
           href="cpu.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
         >CPUs</a>
        
        <a class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
           href="motherboards.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
         >Motherboards</a>
         
         <a class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
           href="ram.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
         >Rams</a>

         <a class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
           href="storage.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
         >Storage</a>

         <a class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
           href="cases.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
         >Cases</a>

         <a class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
           href="coolers.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
         >Coolers</a>
      </nav>

      <div class="d-flex mb-3">
        <h4 class="p-2">Recommended</h4>
        <a class="ms-auto p-2" href="search.jsp?srch-term=ALLALLALL<%= request.getParameter("uid") != null ? "&uid=" + request.getParameter("uid") : "" %>">See all </a>
      </div>

  
      <% 
        int smallerNumber = 0, largerNumber = 0;
        do {
          smallerNumber = (int)Math.floor(Math.random() * 10);
          largerNumber = (int)Math.floor(Math.random() * 10*5); 
          if (smallerNumber == 0)
            ++smallerNumber; 
          if (largerNumber == 0)
            largerNumber=20;
        } while ((smallerNumber >= largerNumber) && (largerNumber - smallerNumber != 1)); 
        int smallerNumber2 = 0, largerNumber2 = 0;
        do {
          smallerNumber2 = (int)Math.floor(Math.random() * 10);
          largerNumber2 = (int)Math.floor(Math.random() * 10*5); 
          if (smallerNumber2 == 0)
            ++smallerNumber2; 
          if (largerNumber2 == 0)
            largerNumber2=20;
        } while ((smallerNumber2 >= largerNumber2) && (largerNumber2 - smallerNumber2 != 1)); 
      %>

      <div class="container-fluid py-2">
        <div class="d-flex flex-row flex-nowrap overflow-auto">
            <% 
            try{
              String listing = "select * from listings where id > " + smallerNumber + " and id <" + largerNumber;
              ResultSet rset = stmt.executeQuery(listing);
              List typeList = new ArrayList(); 
              List listingIDList =  new ArrayList(); 
              List itemIDList = new ArrayList(); 
              int count = 0;
              int i=0;
              while (rset.next()){                
                typeList.add(rset.getString("type"));
                listingIDList.add(rset.getInt("id"));
                itemIDList.add(rset.getInt("itemID"));
                count++;
              }
              while(i<count){
                String getitems = "SELECT " + typeList.get(i) + ".* FROM " + typeList.get(i) + ",listings WHERE listings.id=" + listingIDList.get(i) + " and " + typeList.get(i) + ".id=" + itemIDList.get(i);
                ResultSet itemset = stmt.executeQuery(getitems);
                  while(itemset.next()){
                  String link = itemset.getString("link");
                  String name = itemset.getString("name");
                  Float price = itemset.getFloat("price");
              %>
                <div class="card card-body" style="height: 500px;min-width: 300px;">
                  <img src="<%= link %>" alt="<%= name %>"" style="height: 200px;width: 200px;">
                  <form method="get" action="viewListing">
                    <h3 style="width: 200px;"><%= name %></h3>
                    <p><%out.print(priceFormatter.format(price));%></p>
                    <input hidden name="listingId" value="<%=listingIDList.get(i) %>">
                    <% if(request.getParameter("uid") != null) { %>
                      <input type="hidden" name="uid" value="<%=request.getParameter("uid")%>">
                    <% } %>
                    <button type="submit" class="btn bg_orange">View Listing</button>
                  </form>
                </div>
                <%}i++;
              }
            } catch (SQLException e) {
              e.printStackTrace();
              
            }
            %>
          </div>                
      </div>

      <div class="d-flex mb-3 mt-5">
        <h4 class="p-2">Ready Stock</h4>
        <a class="ms-auto p-2" href="search.jsp?srch-term=ALLALLALL<%= request.getParameter("uid") != null ? "&uid=" + request.getParameter("uid") : "" %>">See all </a>
      </div>
        <div class="container-fluid py-2">
          <div class="d-flex flex-row flex-nowrap overflow-auto">
              <% 
              try{
                String listing = "select * from listings where id > " + smallerNumber2 + " and id <" + largerNumber2;
                ResultSet rset = stmt.executeQuery(listing);
                List typeList = new ArrayList(); 
                List listingIDList =  new ArrayList(); 
                List itemIDList = new ArrayList(); 
                int count = 0;
                int i=0;
                while (rset.next()){                
                  typeList.add(rset.getString("type"));
                  listingIDList.add(rset.getInt("id"));
                  itemIDList.add(rset.getInt("itemID"));
                  count++;
                }
                while(i<count){
                  String getitems = "SELECT " + typeList.get(i) + ".* FROM " + typeList.get(i) + ",listings WHERE listings.id=" + listingIDList.get(i) + " and " + typeList.get(i) + ".id=" + itemIDList.get(i);
                  ResultSet itemset = stmt.executeQuery(getitems);
                    while(itemset.next()){
                    String link = itemset.getString("link");
                    String name = itemset.getString("name");
                    Float price = itemset.getFloat("price");
                %>
                  <div class="card card-body" style="height: 500px;min-width: 300px;">
                    <img src="<%= link %>" alt="<%= name %>"" style="height: 200px;width: 200px;">
                    <form method="get" action="viewListing">
                      <h3 style="width: 200px;"><%= name %></h3>
                      <p><%out.print(priceFormatter.format(price));%></p>
                      <input hidden name="listingId" value="<%=listingIDList.get(i) %>">
                      <% if(request.getParameter("uid") != null) { %>
                        <input type="hidden" name="uid" value="<%=request.getParameter("uid")%>">
                      <% } %>
                      <button type="submit" class="btn bg_orange">View Listing</button>
                    </form>
                  </div>
                  <%}i++;
                }
              } catch (SQLException e) {
                e.printStackTrace();
                
              }
              %>
            </div>                
        </div>
      </div>
      


    </div>

    <div class="footer">
      <footer class="site-footer">
        <div class="container">
          <div class="row">
            <div class="col-sm-12 col-md-6">
              <h6>About</h6>
              <p class="text-justify">
                OldEgg <i>Your Trusted PC Store </i> is an initiative to help
                the community of gamers and developers with their pc needs.
                OldEgg focuses on providing the most trusted and hassle free
                platform to obtain your pc parts. We will ensure you have a
                reliably working workhorse to push through all your needs.
              </p>
            </div>

            <div class="col-xs-6 col-md-3">
              <h6>Categories</h6>
              <ul class="footer-links">
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="gpu.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
                    >GPUs</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="cpu.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
                    >CPUs</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="motherboards.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
                    >Motherboards</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="ram.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
                    >Rams</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="storage.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
                    >Storage</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="cases.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
                    >Cases</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="coolers.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
                    >Coolers</a
                  >
                </li>
              </ul>
            </div>
          </div>
          <hr />
        </div>
        <div class="container">
          <div class="row">
            <div class="col-md-8 col-sm-6 col-xs-12">
              <p class="copyright-text">
                Copyright &copy; 2023 All Rights Reserved by
                <a href="#">Ng Hsin-Kai</a> and <a href="#">Irfan Syakir</a>
              </p>
            </div>

            <div class="col-md-4 col-sm-6 col-xs-12">
              <ul class="social-icons">
                <li>
                  <a class="facebook bg_slategrey" href="#"
                    ><i class="fa fa-facebook"></i
                  ></a>
                </li>
                <li>
                  <a class="twitter bg_slategrey" href="#"
                    ><i class="fa fa-twitter"></i
                  ></a>
                </li>
                <li>
                  <a class="dribbble bg_slategrey" href="#"
                    ><i class="fa fa-dribbble"></i
                  ></a>
                </li>
                <li>
                  <a class="linkedin bg_slategrey" href="#"
                    ><i class="fa fa-linkedin"></i
                  ></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </footer>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
      crossorigin="anonymous"
    >

          document.getElementById('tile').addEventListener('click', cl_Div);

          function cl_Div() {

          document.getElementById('tile').innerHTML = "Welcome to JavaScript";

      }
    </script>
  </body>
</html>
