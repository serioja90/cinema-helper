<%@page import="models.Schedule"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Film[] films = (Film[])request.getAttribute("films"); %>
<% if(films != null && films.length > 0){ %>
  <% for(Film film : films){ %>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h4>
          <strong>
            <a href="<%= film.get("link") %>" target="_blank">
              <%= film.get("title") %>
            </a>
          </strong>
          <span class="pull-right label label-info"><%= film.get("tecnology") %></span>
        </h4>
      </div>
      <div class="panel-body">
        <div class="media">
          <a class="pull-left" href="<%= film.get("link") %>" target="_blank">
            <img class="media-object img-thumbnail" src="<%= film.get("image") %>" alt="<%= film.get("title") %>" style="max-height: 300px;">
          </a>
          <div class="media-body">
            <% if(film.get("genre") != null){ %>
              <p><strong>Genere: </strong> <%= film.get("genre") %></p>
            <% } %>
            <p><strong>Durata: </strong> <%= film.get("duration") %></p>
            <% if(film.get("nation") != null){ %>
              <p><strong>Nazione: </strong> <%= film.get("nation") %></p>
            <% } %>
            <% if(film.get("release_year") != null){ %>
              <p><strong>Anno: </strong> <%= film.get("release_year") %></p>
            <% } %>
            <% if(film.get("director") != null){ %>
              <p><strong>Regia di: </strong> <%= film.get("director") %></p>
            <% } %>
            <% if(film.get("film_cast") != null){ %>
              <p><strong>Cast: </strong> <%= film.get("film_cast") %></p>
            <% } %>
            <p>
              <strong>Descrizione: </strong><br>
              <%= film.get("description") %>
            </p>
            
            <ul class="nav nav-tabs" role="tablist">
              <% String last = null, date = null; %>
              <% boolean first = true; %>
              <% Schedule[] calendar = film.getCalendar(); %>
              <% for(Schedule schedule : calendar){ %>
                <% date = schedule.getDate(); %>
                <% if(!date.equals(last)){ %>
                  <li class="<%= first ? "active" : "" %>">
                    <a href="#<%= schedule.get("film_id") %>-<%= date %>" role="tab" data-toggle="tab">
                      <%= schedule.getDay() %>
                    </a>
                  </li>
                  <% last = date; %>
                <% } %>
                <% if(first) first = false; %>
              <% } %>
            </ul>
            <p class="clearfix"></p>
            <div class="tab-content">
              <% first = true; %>
              <% last = date = null; %>
              <% for(Schedule schedule : calendar){ %>
                <% date = schedule.getDate(); %>
                <% if(!date.equals(last)){ %>
                  <% last = date; %>
                  <% if(!first) { %></h4></div><% } %>
                  <div class="tab-pane <%= first ? "active" : "" %>" id="<%= schedule.get("film_id") %>-<%= date %>">
                    <h5><strong><%= schedule.formatSchedule("EEEEEE d MMMMMM") %></strong><h5><h4>
                <% } %>
                <span class="label label-danger"><%= schedule.getHour() %></span>
                <% if(first) first = false; %>
              <% } %>
              <% if(calendar.length > 0){ %></h4></div><% } %>
            </div>
          </div>
        </div>
      </div>
    </div>
  <% } %>
<% } else { %>
  <h3 class="text-danger">Nessun film trovato!</h3>
<% } %>