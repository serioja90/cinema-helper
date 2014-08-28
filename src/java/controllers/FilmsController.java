/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Film;
import utilities.Logger;

/**
 *
 * @author sergiu
 */
@WebServlet(name = "FilmsController", 
            urlPatterns = {
                            "/films",
                            "/films/index",
                            "/films/genre",
                            "/films/search"
                          })
public class FilmsController extends ApplicationController {
  
  /**
   *
   * @param request
   * @param response
   */
  public void index(HttpServletRequest request, HttpServletResponse response){
    try{
      request.setAttribute("films", Film.getFilmsList());
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("error", ex);
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
      request.setAttribute("error", ex);
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
      request.setAttribute("error", ex);
    }
  }
}
