<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
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
  </head>
  <body class="bg_default">
    <nav
      class="navbar sticky-top navbar-expand-lg navbar-light bg_default"
      style="padding: 20px 50px 15px 50px"
    >
      <a
        class="navbar-brand"
        href="index.jsp<%= request.getParameter("uid") != null ? "?uid=" + request.getParameter("uid") : "" %>"
        style="padding-bottom: 15px; padding-right: 50px;"
        
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
    <div class="row pt-5">
        <div class="col-lg-2"></div>
        <div class="container-fluid col-lg-6 ">
            <h5>Are you sure you want to sign out?</h5>
            <a class="btn bg_red" href="index.jsp">Sign out</a>
            <a class="btn bg_orange" href="index.jsp?uid=<%= request.getParameter("uid")%>">
                Stay signed in 
            </a>
        </div>
        <div class="col-lg-4"></div>
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
  </body>
</html>
