/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.Logger;

/**
 *
 * @author sergiu
 */
public class ApplicationController extends HttpServlet {
  
  public static final String HOME = "films/index";

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods, logs request info and saves generated <code>jsp-path</code> attribute
   * to servlet attributes, in order to make it available for other servlets and jsp
   * pages.
   *
   * @param request servlet request
   * @param response servlet response
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response){
    String method = request.getMethod();
    String url = request.getRequestURL().toString();
    String query = request.getQueryString();
    String ip = request.getRemoteAddr();
    String[] parts = request.getServletPath().split("/");
    String controller = parts[1];
    String action = (parts.length >= 3 ? parts[2] : "index");
    Logger.info(method + " " + url + (query == null ? "" : "?" + query) + " from " + ip);
    request.setAttribute("jsp-path", "/WEB-INF/views/" + controller +  "/" + action );
    request.setAttribute("controller", controller);
    request.setAttribute("action", action);
    Map<String,String[]> params = request.getParameterMap();
    Gson json = new Gson();
    Logger.info("Parameters: " + json.toJson(params));
    Logger.info("Processing " + controller + "#" + action);
  }
  
  /**
   * Forwards the request to a specific jsp page, according to the <code>path</code>
   * parameter passed as argument.
   *
   * @param request servlet request
   * @param response servlet response
   */
  protected void forwardRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String path = (String)request.getAttribute("jsp-path");
    String controller = (String)request.getAttribute("controller");
    String action = (String)request.getAttribute("action");
    path += ".jsp";
    try{
      try{
        Method method = this.getClass().getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
        method.invoke(this, request, response);
      }catch(NoSuchMethodException ex){
        Logger.warn(controller + "#" + action + " not found!");
      }
      Logger.info("Responding with " + path);
      request.getRequestDispatcher(path).forward(request, response);
    }catch(ServletException ex){
      Logger.reportException(ex);
      response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.toString());
    }catch(SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException ex){
      Logger.reportException(ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.toString());
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
    forwardRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
    forwardRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
