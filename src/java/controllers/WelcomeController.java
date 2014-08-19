/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    request.setAttribute("name", "Sergiu bla bla bla");
  }
}
