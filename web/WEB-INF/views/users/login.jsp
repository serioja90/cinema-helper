<%@ page pageEncoding="UTF-8" %>
<% String email = request.getParameter("email"); %>
<div class="container">
  <div class="page-header">
    <div class="row">
      <h1 class="col-lg-12">Login</h1>
    </div>
  </div>
  <%@include file="../../jspf/messages.jspf" %>
  <form role="form" method="POST" class="col-xs-4 col-xs-offset-4" action="<%= request.getContextPath() %>/users/authenticate">
    <div class="form-group">
      <label for="email">Indirizzo email</label>
      <input type="email" class="form-control" id="email" name="email" placeholder="Email"
             value="<%= email == null ? "" : email %>">
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" class="form-control" id="password" name="password" placeholder="Password">
    </div>
    <button type="submit" class="btn btn-primary"><i class="fa fa-fw fa-sign-in"></i> Entra</button>
    <a href="<%= request.getContextPath() %>/users/registration" class="btn btn-info">
       <i class="fa fa-fw fa-magic"></i> Registrazione
    </a>
  </form>
</div>

