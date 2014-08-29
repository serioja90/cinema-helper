<%@page import="models.User"%>
<%@ page pageEncoding="UTF-8" %>
<div class="container">
  <div class="page-header">
    <div class="row">
      <h1 class="col-lg-12">Profilo di <span class="text-info"><%= user.getEmail() %></span></h1>
    </div>
  </div>
  <%@include file="../../jspf/messages.jspf" %>
  
  <dl class="dl-horizontal">
    <dt>Cognome:</dt>
    <dd><%= user.get("surname") %></dd>
    <dt>Nome:</dt>
    <dd><%= user.get("name") %></dd>
    <dt>Username:</dt>
    <dd><%= user.get("username") %></dd>
    <dt>Email:</dt>
    <dd><%= user.get("email") %></dd>
    <dt>Ruolo:</dt>
    <dd><%= user.getRole() %></dd>
  </dl>
</div>
