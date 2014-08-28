<%@ page pageEncoding="UTF-8" %>
<% String username = request.getParameter("username"); %>
<% String email = request.getParameter("email"); %>
<% String surname = request.getParameter("surname"); %>
<% String name = request.getParameter("name"); %>
<div class="container">
  <div class="page-header">
    <div class="row">
      <h1 class="col-lg-12">Registrazione</h1>
    </div>
  </div>
  <%@include file="../../jspf/messages.jspf" %>
  <form role="form" class="col-xs-4 col-xs-offset-4" method="POST" action="<%= request.getContextPath() %>/users/register">
    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" class="form-control" id="username" name="username" placeholder="Username"
             value="<%= username == null ? "" : username %>">
    </div>
    <div class="form-group">
      <label for="email">Indirizzo email</label>
      <input type="email" class="form-control" id="email" name="email" placeholder="Email"
             value="<%= email == null ? "" : email %>">
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" class="form-control" id="password" name="password" placeholder="Password">
    </div>
    <div class="form-group">
      <label for="confirm_password">Conferma Password</label>
      <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="Conferma Password">
    </div>
    <div class="form-group">
      <label for="surname">Cognome (facoltativo)</label>
      <input type="text" class="form-control" id="surname" name="surname" placeholder="Cognome"
             value="<%= surname == null ? "" : surname %>">
    </div>
    <div class="form-group">
      <label for="name">Nome (facoltativo)</label>
      <input type="text" class="form-control" id="name" name="name" placeholder="Nome"
             value="<%= name == null ? "" : name %>">
    </div>
    <button type="submit" class="btn btn-primary"><i class="fa fa-fw fa-save"></i> Invia</button>
    <button type="reset" class="btn btn-danger"><i class="fa fa-fw fa-eraser"></i> Reset</button>
    <a href="<%= request.getContextPath() %>/users/login" class="btn btn-info">
      <i class="fa fa-fw fa-lock"></i> Login
    </a>
  </form>
</div>