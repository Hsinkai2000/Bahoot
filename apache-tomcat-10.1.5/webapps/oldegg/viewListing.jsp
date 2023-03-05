<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link href="./style/style.css" rel="stylesheet" type="text/css"/>
  </head>
  <body class="bg_default">
    <nav
      class="navbar sticky-top navbar-expand-lg navbar-light bg_default"
      style="padding: 20px 50px 15px 50px"
    >
      <a
        class="navbar-brand"
        href="#"
        style="padding-bottom: 15px; padding-right: 50px"
      >
        <img
          src="./images/oldegg-logo-transparent.png"
          width="150dp"
          height="50dp"
          alt="OldEgg"
        />
      </a>
      <form class="navbar-form" role="search">
        <div class="input-group" style="width: 40em">
          <input
            type="text"
            class="form-control pl-5"
            placeholder="Search parts and sellers"
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

    <div class="container pt-5">
      <div class="row">
        <div class="col-lg-6">
          <div class="row">
            <div class="col-lg-12">
              <img
                class="zoom"
                
                src="<%= response.getHeader("link") %>"
              />
            </div>
          </div>
          <div class="row">
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
            <div class="col-lg-12 pb-3">
              <button class="btn bg_orange" style="width: 400px">
                Buy Now
              </button>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-12 pb-3">
              <button class="btn bg_orange" style="width: 400px">
                Add to Cart
              </button>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-12 pb-3">
              <p>Sold by <a href="#" class="underline">Anonymous panda</a></p>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-12 pb-3">
              <p class="text-justify">
                <%= response.getHeader("itemInfo") %>
              </p>
            </div>
          </div>
        </div>
      </div>
      <div class="pb-4 pt-4"></div>

      <div class="d-flex mb-3">
        <h4>Similar Items</h4>
        <a class="ms-auto">see all ></a>
      </div>

      <div
        class="d-flex flex-row flex-nowrap overflow-auto pb-2"
        style="height: 300px"
      >
        <div class="card card-block mx-2" style="min-width: 300px">Card</div>
        <div class="card card-block mx-2" style="min-width: 300px">Card</div>
        <div class="card card-block mx-2" style="min-width: 300px">Card</div>
        <div class="card card-block mx-2" style="min-width: 300px">Card</div>
        <div class="card card-block mx-2" style="min-width: 300px">Card</div>
        <div class="card card-block mx-2" style="min-width: 300px">Card</div>
        <div class="card card-block mx-2" style="min-width: 300px">Card</div>
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
                  <a class="flex-sm-fill text-sm nav-link" href="#">All</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="#">GPUs</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="#">CPUs</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="#"
                    >Motherboards</a
                  >
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="#">Rams</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="#">Storage</a>
                </li>
                <li>
                  <a class="flex-sm-fill text-sm nav-link" href="#">Cases</a>
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
