/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.Model;
import models.Role;

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
   * @throws java.lang.Exception
   */
  public void index(HttpServletRequest request, HttpServletResponse response) throws Exception{
    ArrayList<Model> roles = new Role().find("1");
    request.setAttribute("name", "Sergiu bla bla bla" + roles.get(0).get("name"));
  }
}
