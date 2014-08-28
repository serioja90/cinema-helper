<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Film"%>
<div class="container">
  <div class="page-header">
    <div class="row">
      <h1 class="col-lg-12">Risultati per <small>"<%= request.getAttribute("query") %>"</small></h1>
    </div>
  </div>
  <%@include file="_films_list.jsp" %>
</div>
