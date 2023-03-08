<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import = "java.io.*,java.util.*,java.sql.*,java.text.*"%>

<% Connection conn = DriverManager.getConnection(
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
    <title>Old Egg | Checkout</title><link rel="icon" href="./images/oldegg-icon.ico" type="image/x-icon">
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
      <form method ="get" action = "search.jsp" class="navbar-form" role="search">
        <div class="input-group" style="width: 40em">
          <input
            type="text"
            class="form-control pl-5"
            placeholder="Search parts"
            name="srch-term"
            id="srch-term-header"
          />
          <input hidden name="uid" <% if(request.getParameter("uid") != null) {%>value="<%=request.getParameter("uid") %>"<% } else {%>value="" <%}%> />
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

    <div class="container pt-5">
      <div class="row">
        <div class="col-lg-6" style="padding-left: 200px;">
            <h1>Checkout</h1>
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
                    
                    double totalprice =0.0;
                    while(i<count){
                        String getitems = "SELECT " + typeList.get(i) + ".* FROM " + typeList.get(i) + ",listings WHERE listings.id=" + listingIDList.get(i) + " and " + typeList.get(i) + ".id=" + itemIDList.get(i);
                    ResultSet itemset = stmt.executeQuery(getitems);
                        while(itemset.next()){
                            String link = itemset.getString("link");
                            String name = itemset.getString("name");
                            Float price = itemset.getFloat("price");
                            
                            totalprice+=price;%>
                            <div class="row pb-3">
                            <div class="col-lg-3"><img src="<%=link%>"class="card-img-top" alt="..." style="max-height: 150px;max-width: 150px;height: 100px;width: 100px;"></div>
                            <div class="col-lg-9">                            
                                <h6><%=name%></h6>
                                <div class="row">
                                    <h6 id="hiddenprice_<%=i%>">Price: $<%= price %></h6>
                                </div>
                            </div>                          
                            </div>
                            <hr>
                            <%i++;}
                            }%>
                            <div class="row">
                                <h6>Total Price: $<%= String.format("%.2f",totalprice) %></h6>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <% 
                                String sqlUsr = "select * from users where id = " +uid;
                                ResultSet usrRS = stmt.executeQuery(sqlUsr);
                                usrRS.next();
                            %>
                            <div class="row"></div>
                            <div class="row">
                              <form id="checkout-form" method="post" action="groupCheckout">
                                <input type="hidden" name="product_id" value="<%=
                                request.getParameter("product_id") %>">
                                <div class="form-group pb-3">
                                  <label for="name">Name</label>
                                  <input type="text" class="form-control" id="name" name="name"
                                  placeholder="Enter your name" value="<%=
                                  usrRS.getString("name") %>" required />
                                </div>
                                <div class="form-group pb-3">
                                  <label for="email">Email address</label>
                                  <input type="email" class="form-control" id="email" name="email"
                                  placeholder="Enter your email" value="<%=
                                  usrRS.getString("email") %>" required />
                                </div>
                                <div class="form-group pb-3">
                                  <label for="address">Address</label>
                                  <input
                                    type="text"
                                    class="form-control"
                                    id="address"
                                    name="address"
                                    placeholder="Enter your address"
                                    required
                                  />
                                </div>
                                <div class="form-group pb-3">
                                  <label for="card-number">Card Number</label>
                                  <input
                                    type="number"
                                    class="form-control"
                                    id="card-number"
                                    name="card_number"
                                    placeholder="Enter your card number"
                                    required
                                  />
                                </div>
                                <div class="form-group pb-3">
                                  <label for="expiry-date">Expiry Date</label>
                                  <span>
                                    <select id="month" name="month">
                                      <option >January</option>
                                      <option>February</option>
                                      <option selected>March</option>
                                      <option>April</option>
                                      <option>May</option>
                                      <option>June</option>
                                      <option>July</option>
                                      <option>August</option>
                                      <option>September</option>
                                      <option>October</option>
                                      <option>November</option>
                                      <option>December</option>
                                    </select>
                                  </span>
                                  <span>
                                    <label for="year">Year:</label>
                                    <select id="year" name="year">
                                      <option >2023</option>
                                      <option selected>2024</option>
                                      <option >2025</option>
                                      <option>2026</option>
                                      <option>2027</option>
                                      <option>2028</option>
                                      <option>2029</option>
                                    </select>
                                  </span>
                                </div>
                                <div class="form-group pb-3">
                                  <label for="cvv">CVV</label>
                                  <input
                                    type="number"
                                    class="form-control"
                                    id="cvv"
                                    name="cvv"
                                    placeholder="Enter your CVV"
                                    maxlength="3"
                                    required
                                  />
                                </div>
                                <% if(request.getParameter("uid") != null) { %>
                                    <input hidden type="hidden" name="uid" value="<%=request.getParameter("uid")%>">
                                  <% } %>
                                <div class="row">
                                  <div class="col-lg-6">
                                    <button type="submit" class="btn bg_orange mt-3">
                                      Purchase
                                    </button>
                                  </div>
                                  <div class="col-lg-6">
                                    <a class="flex-sm-fill text-sm nav-link" href="index.jsp?uid=<%= request.getParameter("uid") != null ? request.getParameter("uid") : "" %>">Cancel</a>
                                  </div>
                                </div>
                              </form>
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
    ></script>
    <script>
      var quantityInput = document.getElementById("quantity");
      var priceOutput = document.getElementById("price");
      var pricePerUnit = document.getElementById("hiddenprice");

      function updatePrice() {
        var quantity = quantityInput.value;
        var price = quantity * pricePerUnit.innerHTML;
        priceOutput.textContent = "Price: $" + price.toFixed(2);
      }

      function increment() {
        quantityInput.value++;
        updatePrice();
      }

      function decrement() {
        if (quantityInput.value > 1) {
          quantityInput.value--;
          updatePrice();
        }
      }

      updatePrice();
    </script>
  </body>
</html>
