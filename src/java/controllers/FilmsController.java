package controllers;

import com.google.gson.Gson;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Film;
import utilities.Logger;

@WebServlet(name = "FilmsController", 
            urlPatterns = {
                            "/films",
                            "/films/index",
                            "/films/genre",
                            "/films/search"
                          })
public class FilmsController extends ApplicationController {
  
  public void index(HttpServletRequest request, HttpServletResponse response){    
    try{
      String contentType = request.getContentType();
      if(contentType != null && contentType.equals("application/json")){
        Gson json = new Gson();
        ArrayList<Object> films = new ArrayList<Object>();
        for(Film film : Film.getFilmsList()){
          films.add(film.getProperties());
        }
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().print(json.toJson(films));
      }else{
        request.setAttribute("films", Film.getFilmsList());
      }
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
  
  public void genre(HttpServletRequest request, HttpServletResponse response){
    try{
      String genre = request.getParameter("genre");
      Film[] films = Film.getFilmsByGenre(genre);
      request.setAttribute("genre", genre);
      request.setAttribute("films", films);
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
  
  public void search(HttpServletRequest request, HttpServletResponse response){
    try{
      String query = request.getParameter("query");
      Film[] films = Film.search(query);
      request.setAttribute("query", query);
      request.setAttribute("films", films);
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
}
