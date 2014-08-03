<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <%@include file="WEB-INF/jspf/head.jspf" %>
  <body>
    <%@include file="WEB-INF/jspf/navbar.jspf" %>
    <div class="container">
      <div class="page-header">
        <div class="row">
          <h1 class="col-lg-12">Fast News</h1>
        </div>
      </div>
      <div id="header">
          <form id="login" class="loginForm" action="login.jsp" method="POST" style="text-align: right;">
              <div style="display: inline-block">
                  <label>Email:</label>
                  <input type="text" name="email" />
              </div>
              <div style="display: inline-block">
                  <label>Password:</label>
                  <input type="text" name="password" />
              </div>
              <div style="display: inline-block">
                  <input type="submit" value="Login" />
              </div>
          </form>
      </div>
      <div id="registration">
        <form id="registration" class="registrationForm" action="registration.jsp" method="POST">
          <div>
            <label>Cognome:</label>
            <input type="text" name="surname" />
          </div>
          <div>
            <label>Nome:</label>
            <input type="text" name="name" />
          </div>
          <div>
            <input type="radio" name="gender" value="M" checked />Maschio
            <input type="radio" name="gender" value="F" />Femmina
          </div>
        </form>
      </div>   
      <div id="content"></div>
      <%@include file="WEB-INF/jspf/footer.jspf" %>
    </div>
  </body>
</html>