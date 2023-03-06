<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>OldEgg | Login</title>
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
    <div class="d-flex">
      <img
        class="flex-fill w-50 vh-100"
        style="object-fit: cover; object-position: bottom"
        src="./images/andre_tan_setup.jpg"
      />

      <div class="flex-fill align-self-center container w-50">
        <div class="row pb-4">
          <div class="col"></div>
          <div class="col">
            <a href="index.html">
              <img src="./images/oldegg-icon.png" width="200px" />
            </a>
          </div>
          <div class="col"></div>
        </div>
        <div class="row pb-4">
          <div class="col-3"></div>
          <div class="col-6">
            <h2>Login</h2>
          </div>
          <div class="col"></div>
        </div>
        <form  method="post" action="signup">
          <div class="form-group pb-3">
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <label for="inputEmail"><h7>Email</h7></label>
              </div>
              <div class="col-3"></div>
            </div>
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <input
                  type="email"
                  class="form-control"
                  id="inputEmail"
                  name="email"
                  value="${email}"
                  required
                />
              </div>
              <div class="col-3"></div>
            </div>
          </div>
          <div class="form-group pb-4">
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <label for="inputName"><h7>Name</h7></label>
              </div>
              <div class="col-3"></div>
            </div>
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <input
                  type=""
                  class="form-control"
                  id="inputName"
                  name="name"
                  value="${name}"
                  required
                />
              </div>
              <div class="col-3"></div>
            </div>
          </div>
          <div class="form-group pb-4">
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <label for="inputMobile"><h7>Mobile Number</h7></label>
              </div>
              <div class="col-3"></div>
            </div>
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <input
                  type="tel"
                  class="form-control"
                  id="inputMobile"
                  name="mobile"
                  required
                />
              </div>
              <div class="col-3"></div>
            </div>
          </div>
          <div class="form-group pb-4">
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <label for="inputPassword"><h7>Password</h7></label>
              </div>
              <div class="col-3"></div>
            </div>
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <input
                  type="password"
                  class="form-control"
                  id="inputPassword"
                  name="password"
                  value="${password}"
                  minlength="8"
                  required
                />
              </div>
              <div class="col-3"></div>
            </div>
          </div>
          <div class="form-group pb-4">
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <label for="inputConfirm"><h7>Confirm Password</h7></label>
              </div>
              <div class="col-3"></div>
            </div>
            <div class="form-row row">
              <div class="col-3"></div>
              <div class="col-6">
                <input
                  type="tel"
                  class="form-control"
                  id="inputConfirm"
                  name="confirm"
                  required
                />
              </div>
              <div class="col-3"></div>
            </div>
          </div>
          <div class="row">
            <div class="col-3"></div>
            <div class="col-6">
              <% if(request.getParameter("data") != null) { %>
                <p style="color: red;"> <%= request.getParameter("data")%></p>
              <%} else{%>
                <p></p>
              <%} %>
            </div>
            <div class="col-3"></div>
          </div>
          <div class="row">
            <div class="col-3"></div>
            <div class="col-6">
              <button type="submit" class="btn bg_orange" action="signupservlet">
                Sign Up
              </button>
            </div>
            <div class="col"></div>
          </div>
        </form>
      </div>
    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
