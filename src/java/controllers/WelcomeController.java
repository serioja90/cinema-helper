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
@WebServlet(name = "WelcomeController", 
            urlPatterns = {
                            "/welcome",
                            "/welcome/index",
                            "/welcome/hello"
                          })
public class WelcomeController extends ApplicationController {
  
  /**
   *
   * @param request
   * @param response
   */
  public void index(HttpServletRequest request, HttpServletResponse response){
    try{
      request.setAttribute("films", Film.getFilmsList());
      request.setAttribute("name", "Ciao");
    }catch(Exception ex){
      Logger.reportException(ex);
    }
  }
}
