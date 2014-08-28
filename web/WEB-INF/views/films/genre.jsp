<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Film"%>
<div class="container">
  <div class="page-header">
    <div class="row">
      <h1 class="col-lg-12"><%= request.getAttribute("genre") %></h1>
    </div>
  </div>
  <%@include file="_films_list.jsp" %>
</div>