package com.clinvest.migraine.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.clinvest.migraine.server.data.Database;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger("MIGRAINE");
  
  @Override
  public void init() {
    LOG.debug("Admin servlet initialized.");
  }
  
  /*
   * /admin/initdb?auth=<token>
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String pathInfo = request.getPathInfo();
    String[] parts = pathInfo.split("/");
    String authToken = request.getParameter("auth");
    // Check auth token
    if (null != authToken && Authorize.checkAuthToken(authToken))
    {
      LOG.debug("Initializing migraine database.");
      try
      {
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        Database.initialize();
        response.getWriter().println("Database initialized.");
        LOG.debug("Database initialized.");
      }
      catch (Exception e)
      {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        LOG.error("Error initializing migraine database.", e);
      }  
    }
    else
    {
      LOG.info("Unauthorized request.");
      response.sendError(401, "Unauthorized");
    }
  }
    
    
}
