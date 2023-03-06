<%@ page import = "java.io.*,java.util.*,java.sql.*,java.text.*"%>

<% 
DecimalFormat priceFormatter = new DecimalFormat("$#0.00");
Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/oldegg?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                           "root", "password");
Statement stmt = conn.createStatement();
%>

<%!
  public int getRandomNumber (int max, int min){
    return (int) ((Math.random() * (max - min)) + min);
  }
%>

<%!
  public String getRandomProduct(int i){
    String product = "";
    switch(i){
      case 1: product = "gpus";
      break;

      case 2: product = "cpus";
      break;

      case 3: product = "motherboards";
      break;

      case 4: product = "rams";
      break;

      case 5: product = "storages";
      break;

      case 6: product = "cases";
      break;

      case 7: product = "coolers";
      break;

      default:;
    }
    return product;
    }
%>

<%!
  public String getSQLStr(String product, int id){
    String sqlStr = "";
    switch(product){
      case "gpus": sqlStr = "SELECT * FROM gpus WHERE id = " + id;
      break;

      case "cpus": sqlStr = "SELECT * FROM cpus WHERE id = " + id;
      break;

      case "motherboards": sqlStr = "SELECT * FROM motherboards WHERE id = " + id;
      break;

      case "rams": sqlStr = "SELECT * FROM rams WHERE id = " + id;
      break;

      case "storages": sqlStr = "SELECT * FROM storage WHERE id = " + id;
      break;

      case "cases": sqlStr = "SELECT * FROM cases WHERE id = " + id;
      break;

      case "coolers": sqlStr = "SELECT * FROM coolers WHERE id = " + id;
      break;

      default:;
    }
    return sqlStr;
    }
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
        href="index.jsp"
        style="padding-bottom: 15px; padding-right: 50px"
      >
        <img
          src="./images/oldegg-logo-transparent.png"
          width="150dp"
          height="50dp"
          alt="OldEgg"
        />
      </a>
      <form method="get" action ="search.jsp" class="navbar-form" role="search">
        <div class="input-group" style="width: 40em">
          <input
            type="text"
            class="form-control pl-5"
            placeholder="Search parts"
            name="srch-term"
            id="srch-term-header"
          />
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
          <a class="nav-item nav-link" href="login.jsp"
            ><img src="./images/btn-account.svg" alt="Wishlist" height="30dp"
          /></a>
        </div>
      </div>
    </nav>

    <div style="padding-left: 50px; padding-right: 50px">
      <span class="inline" style="color: #7541b0">SHOP CATEGORIES:</span>
      <nav class="nav nav-pills flex-column flex-sm-row inline pb-5 pt-1">
        <a
          class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
          href="gpu.jsp"
          >GPUs</a
        >
        <a
          class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
          href="cpu.jsp"
          >CPUs</a
        >
        <a
          class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
          href="motherboards.jsp"
          >Motherboards</a
        >
        <a
          class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
          href="ram.jsp"
          >Rams</a
        >
        <a
          class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
          href="storage.jsp"
          >Storage</a
        >
        <a
          class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
          href="cases.jsp"
          >Cases</a
        >
        <a
          class="flex-sm-fill text-sm-center nav-link bg_white border50 mx-3"
          href="coolers.jsp"
          >Coolers</a
        >
      </nav>

      <div class="d-flex mb-3">
        <h4 class="p-2">Recommended</h4>
      </div>

      <div class="row row-cols-1 row-cols-md-6 g-4">
          <% for (int i = 0; i < 12; i++) {%>
            <%
                int rng = getRandomNumber(1,7);
                String product = getRandomProduct(rng);
                int id = getRandomNumber(1,9);
                String sqlStr = getSQLStr(product,id);
                ResultSet rset = stmt.executeQuery(sqlStr);
                rset.next();
                
            %>

            <div class="col">
              <div class="card h-100">
                <img src="<%=rset.getString("link")%>"class="card-img-top" alt="...">
                <div class="card-body">
                  
                </div>
                <div class="card-footer">
                  <h5 class="card-text"><%=rset.getString("name")%></h5>
                  <h5 class="card-text"><%out.print(priceFormatter.format(rset.getFloat("price")));%></h5>
                  <form method="get" action="viewListing">
                    <input type="hidden" value="2" name="listingId" />
                    <button type="submit" class="btn btn-primary" >View Listing</button>
                  </form>
                </div>
              </div>
            </div>
          
          <%}%>
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
                  <a class="flex-sm-fill text-sm nav-link" href="gpu.jsp">GPUs</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="cpu.jsp">CPUs</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="motherboards.jsp"
                    >Motherboards</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="ram.jsp">Rams</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="storage.jsp">Storage</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="cases.jsp">Cases</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="coolers.jsp">Coolers</a>
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
    </script>
  </body>
</html>
