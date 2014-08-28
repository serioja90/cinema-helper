<%@page import="models.User"%>
<%@ page pageEncoding="UTF-8" %>
<div class="container">
  <div class="page-header">
    <div class="row">
      <h1 class="col-lg-12">Profilo di <span class="text-info"><%= user.getEmail() %></span></h1>
    </div>
  </div>
  <%@include file="../../jspf/messages.jspf" %>
</div>
