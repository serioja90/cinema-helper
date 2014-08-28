/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import utilities.Logger;

@WebServlet(name = "UsersController", 
            urlPatterns = {
                            "/users",
                            "/users/index",
                            "/users/login",
                            "/users/authenticate",
                            "/users/registration",
                            "/users/register",
                            "/users/logout"
                          })
public class UsersController extends ApplicationController{
  public void index(HttpServletRequest request, HttpServletResponse response){
    try{
      HttpSession session = request.getSession();
      User user = (User)session.getAttribute("user");
      if(user == null){
        response.sendRedirect(request.getContextPath() + "/users/login");
      }else{
        request.setAttribute("user", user);
      }
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
  
  public void login(HttpServletRequest request, HttpServletResponse response){
    try{
      HttpSession session = request.getSession();
      User user = (User)session.getAttribute("user");
      if(user != null) response.sendRedirect(request.getContextPath() + "/users/index");
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
  
  public void authenticate(HttpServletRequest request, HttpServletResponse response){
    try{
      HttpSession session = request.getSession();
      User user = User.authenticate(request.getParameter("email"), request.getParameter("password"));
      if(user != null){
        session.setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/films/index");
      }else{
        request.setAttribute("errors", new String[]{"Login o password errati"});
        request.setAttribute("jsp-path", "/WEB-INF/views/users/login");
      }
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
  
  public void registration(HttpServletRequest request, HttpServletResponse response){
    try{
      HttpSession session = request.getSession();
      User user = (User)session.getAttribute("user");
      if(user != null) response.sendRedirect(request.getContextPath() + "/users/index");
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
  
  public void register(HttpServletRequest request, HttpServletResponse response){
    try{
      HttpSession session = request.getSession();
      User user = (User)session.getAttribute("user");
      String[] errors;
      if(user != null){
        response.sendRedirect(request.getContextPath() + "/users/index");
      }else{
        Map<String,String> params = new HashMap<>();
        params.put("username", request.getParameter("username"));
        params.put("email", request.getParameter("email"));
        params.put("password", request.getParameter("password"));
        params.put("confirm_password", request.getParameter("confirm_password"));
        params.put("surname", request.getParameter("surname"));
        params.put("name", request.getParameter("name"));
        user = User.create(params);
        errors = user.getErrors();
        if(errors.length > 0){
          request.setAttribute("errors", errors);
          request.setAttribute("jsp-path", "/WEB-INF/views/users/registration");
        }else{
          session.setAttribute("user", user);
          response.sendRedirect(request.getContextPath() + "/" + ApplicationController.HOME);
        }
      }
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
  
  public void logout(HttpServletRequest request, HttpServletResponse response){
    try{
      HttpSession session = request.getSession();
      session.removeAttribute("user");
      response.sendRedirect(request.getContextPath() + "/films/index");
    }catch(Exception ex){
      Logger.reportException(ex);
      request.setAttribute("errors", new String[]{ex.toString()});
    }
  }
}
