<%@ page pageEncoding="UTF-8" %>
<%@page import="models.Film"%>
<div class="container">
  <div class="page-header">
    <div class="row">
      <h1 class="col-lg-12">Elenco Film</h1>
    </div>
  </div>
  <%@include file="../../jspf/messages.jspf" %>
  <%@include file="_films_list.jsp" %>
</div>