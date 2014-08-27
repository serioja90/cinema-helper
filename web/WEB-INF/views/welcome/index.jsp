<%@page import="models.Film"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <div class="page-header">
    <div class="row">
      <h1 class="col-lg-12">Welcome#index (<%= request.getAttribute("name") %>)</h1>
    </div>
  </div>
  <% Film[] films = (Film[])request.getAttribute("films"); %>
  <% if(films != null){ %>
    <% for(Film film : films){ %>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h4>
            <strong><a href="<%= film.get("link") %>" target="_blank"><%= film.get("title") %></a></strong>
          </h4>
        </div>
        <div class="panel-body">
          <div class="media">
            <a class="pull-left" href="<%= film.get("link") %>" target="_blank">
              <img class="media-object img-thumbnail" src="<%= film.get("image") %>" alt="<%= film.get("title") %>" style="max-height: 300px;">
            </a>
            <div class="media-body">
              <p><strong>Durata: </strong> <%= film.get("duration") %></p>
              <p>
                <strong>Descrizione: </strong><br>
                <%= film.get("description") %>
              </p>
            </div>
          </div>
        </div>
      </div>
    <% } %>
  <% } %>
</div>