<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import ="java.io.*,java.util.*,java.sql.*,java.text.*"%> 
<% Connection conn = DriverManager.getConnection(
"jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
"root", "password"); Statement stmt = conn.createStatement(); String sqlStr; %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>OldEgg | <%= response.getHeader("name") %></title>
    <link rel="icon" type="image/x-icon" href="./images/oldegg-icon.png" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
      crossorigin="anonymous"
    />
    <link href="./style/style.css" rel="stylesheet" type="text/css" />
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
          <a
            class="nav-item nav-link active btn rounded-pill px-4"
            style="color: aliceblue; background-color: #7541b0"
            href="#"
            ><span class="sr-only">Sell</span></a
          >
          <a class="nav-item nav-link" href="#"
            ><img src="./images/btn-wishlist.svg" alt="Wishlist" height="30dp"
          /></a>
          <a class="nav-item nav-link" href="#"
            ><img src="./images/btn-cart.svg" alt="Wishlist" height="30dp"
          /></a>
          <a class="nav-item nav-link" 
          <%
          if(request.getParameter("uid") != null) {
          %>
              <a href="signout.jsp?uid=<%=request.getParameter("uid")%>"><img src="./images/btn-account.svg" alt="Wishlist" height="30dp"
                /></a>
          <%
            } else {
          %>
              <a href="login.jsp"><img src="./images/btn-account.svg" alt="Wishlist" height="30dp"
                /></a>
          <%
            }
          %>
          </a>
        </div>
      </div>
    </nav>

    <div class="container pt-5">
      <div class="row">
        <div class="col-lg-6">
          <div class="row">
            <div class="col-lg-12">
              <img class="zoom" src="<%= response.getHeader("link") %>" />
            </div>
          </div>
          <div class="row pt-3">
            <div class="col-lg-12">
              <p>
                Have a similar item?
                <a class="underline" href="#">Sell yours</a>
              </p>
            </div>
          </div>
        </div>
        <div class="col-lg-6">
          <div class="row">
            <div class="col-lg-12 pb-3">
              <h2><%= response.getHeader("name") %></h2>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-12 pb-3">
              <h3>$<%= response.getHeader("price") %></h3>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-12">
              <form method="post" action="viewListing">
                <input type="hidden" name="itemID" value=<%= response.getHeader("itemID") %> ></input>
                <% if(request.getParameter("uid") != null) { %>
                  <input type="hidden" name="uid" value="<%=request.getParameter("uid")%>">
                <% } %>
                <input type="hidden" name="name" value="<%= response.getHeader("name") %>" ></input>
                <input type="hidden" name="price" value=<%= response.getHeader("price") %>></input>
                <input type="hidden" name="qty" value=<%= response.getHeader("qty") %> ></input>
                <input type="hidden" name="link" value=<%= response.getHeader("link") %>></input>
                <input type="hidden" name="itemInfo" value="<%= response.getHeader("itemInfo") %>" ></input>
                <input type="hidden" name="action" value="purchase" ></input>
                <input type="hidden" name="itemType" value=<%= response.getHeader("type") %>></input>
                <input hidden name="listingId" value=<%= request.getParameter("listingId") %>></input>
                <input
                style="width: 300px;"
                  type="submit"
                  class="btn bg_orange"
                  value="Purchase"
                />
              </form>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-12 pb-3">
              <form method="post" action="viewListing">
                <input hidden name="listingId" value=<%= request.getParameter("listingId") %>></input>
                <br />
                <% if(request.getParameter("uid") != null) { %>
                  <input type="hidden" name="uid" value="<%=request.getParameter("uid")%>">
                <% } %>
                <input hidden name="action" value="addtocart" type="text" ></input>
                <input
                style="width: 300px;"
                  type="submit"
                  class="btn bg_orange"
                  value="Add to Cart"
                />
              </form>
            </div>
          </div>
          
          <div class="row">
            <div class="col-lg-12 pb-3">
              <p>Sold by <a href="#" class="underline">Anonymous panda</a></p>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-12 pb-3">
              <p class="text-justify"><%= response.getHeader("itemInfo") %></p>
            </div>
          </div>
        </div>
      </div>
      <div class="pb-4 pt-4"></div>

      <div class="d-flex mb-3">
        <h4>Similar Items</h4>
        <a class="ms-auto">see all ></a>
      </div>

      <div class="container-fluid py-2">
        <div class="d-flex flex-row flex-nowrap overflow-auto">
            <% 
            try{
              String listing = "select * from listings where type = '" + response.getHeader("type") +  "'";
              ResultSet rset = stmt.executeQuery(listing);
              List typeList = new ArrayList(); 
              List listingIDList =  new ArrayList(); 
              List itemIDList = new ArrayList(); 
              int count=0;
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
                  <p>$<%= price %></p>
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
                  <a class="flex-sm-fill text-sm nav-link" href="gpu.jsp?uid=<%= request.getParameter("uid") != null ? request.getParameter("uid") : "" %>"
                    >GPUs</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="cpu.jsp?uid=<%= request.getParameter("uid") != null ? request.getParameter("uid") : "" %>"
                    >CPUs</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="motherboards.jsp?uid=<%= request.getParameter("uid") != null ? request.getParameter("uid") : "" %>"
                    >Motherboards</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="ram.jsp?uid=<%= request.getParameter("uid") != null ? request.getParameter("uid") : "" %>"
                    >Rams</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="storage.jsp?uid=<%= request.getParameter("uid") != null ? request.getParameter("uid") : "" %>"
                    >Storage</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="cases.jsp?uid=<%= request.getParameter("uid") != null ? request.getParameter("uid") : "" %>"
                    >Cases</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="coolers.jsp?uid=<%= request.getParameter("uid") != null ? request.getParameter("uid") : "" %>"
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
  </body>
</html>
