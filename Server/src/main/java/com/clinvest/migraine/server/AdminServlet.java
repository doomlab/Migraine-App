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
  private static final long   serialVersionUID = 1L;
  private static final Logger LOG              = LogManager.getLogger("MIGRAINE");

  @Override
  public void init()
  {
    LOG.debug("Admin servlet initialized.");
  }

  /*
   * /admin
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
      response.getWriter().println("Error initializing migraine database.");
      e.printStackTrace(response.getWriter());
      LOG.error("Error initializing migraine database.", e);
    }
  }

}
