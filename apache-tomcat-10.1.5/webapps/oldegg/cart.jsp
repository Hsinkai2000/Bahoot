<%@ page import = "java.io.*,java.util.*,java.sql.*,java.text.*"%>

<% 
DecimalFormat priceFormatter = new DecimalFormat("$#0.00");
Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                           "root", "password");
Statement stmt = conn.createStatement();
int uid = Integer.parseInt(request.getParameter("uid"));
String sqlStr = "select listings.* from listings,carts where listings.id = carts.listingID AND carts.userID = " + uid;
ResultSet rset = stmt.executeQuery(sqlStr);
%>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Old Egg | Home</title>
    <link rel="icon" type="image/x-icon" href="./images/oldegg-icon.png">
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
      crossorigin="anonymous"
    />
    <link href="./style/style.css" rel="stylesheet" />
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
      </div>

      <div class="d-flex mb-3">
      </div>

        <div class="row px-5">
          <div class="col-lg-6" style="padding-left: 200px;">

            <h4>Cart</h4>
            <% List typeList = new ArrayList(); 
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
                if(typeList.size() != 0){
                    double totalprice =0.0;
                    while(i<count){
                        String getitems = "SELECT " + typeList.get(i) + ".* FROM " + typeList.get(i) + ",listings WHERE listings.id=" + listingIDList.get(i) + " and " + typeList.get(i) + ".id=" + itemIDList.get(i);
                    ResultSet itemset = stmt.executeQuery(getitems);
                        while(itemset.next()){
                            String link = itemset.getString("link");
                            String name = itemset.getString("name");
                            Float price = itemset.getFloat("price");
                            %>
                            <div class="row pb-3">
                            <div class="col-lg-3"><img src="<%=link%>"class="card-img-top" alt="..." style="max-height: 150px;max-width: 150px;height: 100px;width: 100px;"></div>
                            <div class="col-lg-9">                            
                                <h6><%=name%></h6>
                                <div class="row">
                                    <h6>Quantity: 1</h6>                                    
                                        
                                        <h6 id="hiddenprice_<%=i%>">Price: $<%= price %></h6>
                                </div>
                            </div>                          
                            </div>
                            <hr>
                            <%i++;totalprice+=price;}
                            }%>
                        </div>

                    <div class="col-lg-3">
                        <div class="row">
                        <div class="col-lg-12"><h4>Cart Information</h4></div>
                        </div>
                        <div class="row">
                        <div class="col-lg-12" class="font-weight-bold">Total Price:</div>
                        </div>
                        <div class="row">
                        <div class="col-lg-12">$<%=String.format("%.2f",totalprice)%></div>
                        </div>
                        <div class="row">
                        <div class="col-lg-12">
                            <form method="get" action="./groupcheckout.jsp">
                                    <% if(request.getParameter("uid") != null) { %>
                                      <input type="hidden" name="uid" value="<%=request.getParameter("uid")%>">
                                    <% } %>
                                    <input
                                    style="width: 300px;"
                                      type="submit"
                                      class="btn bg_orange"
                                      value="Check Out"
                                    />
                            </form>
                        </div>
                        </div>
                    </div>
                <%}else{%>
                    <div class="row pb-3">
                        <div class="col-lg-12">
                        <h6>Check out our listings and add something to cart!</h6>
                    </div></div>
                <%}%>
        </div>
  
    <div class="footer ">
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
    <script>
        function increment(index) {
            // Get the input element using its unique id
            const quantity = document.getElementById(`quantity_${index}`);
            const price = document.getElementById(`hiddenprice_${index}`).innerHTML;
            if (quantity.value < 10) {
            quantity.value++;
            updatePrice(price, quantity.value, index);
            }
        }

        function decrement(index) {
            // Get the input element using its unique id
            const quantity = document.getElementById(`quantity_${index}`);
            const price = document.getElementById(`hiddenprice_${index}`).innerHTML;
            if (quantity.value > 1) {
            quantity.value--;
            updatePrice(price, quantity.value, index);
            }
        }

        function updatePrice(price, quantity, index) {
            const total = price * quantity;
            const priceElement = document.getElementById(`price_${index}`);
            priceElement.innerHTML = `Total: $${total.toFixed(2)}`;
        }

        window.onload = updatePrice();
      </script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
