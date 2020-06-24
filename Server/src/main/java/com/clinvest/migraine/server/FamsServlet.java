package com.clinvest.migraine.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.clinvest.migraine.server.data.FamsEntry;
import com.clinvest.migraine.server.data.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/fams")
public class FamsServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger("MIGRAINE");
  private Gson gson;

  @Override
  public void init()
  {
    LOG.debug("FAMS servlet initialized.");
    gson = new GsonBuilder().setLenient().create();
    
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    User   user      = null;
    String authToken = request.getParameter("auth");
    // Check auth token
    if (null != authToken && Authorize.checkAuthToken(authToken))
    {
      BufferedReader reader = request.getReader();
      String         data   = IOUtils.toString(reader);
      LOG.debug("FAMS save request: " + data);
      if (null != data)
      {
        FamsEntry fe = gson.fromJson(data, FamsEntry.class);

        user = User.getById(fe.getUserId());
        if (null == user)
        {
          LOG.error("Invalid user servlet request: User ID not found.");
          response.sendError(400, "Invalid request: User ID not found.");
          return;
        }

        fe.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        FamsEntry.save(fe);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // create HTML response
        PrintWriter responder = response.getWriter();
        StringWriter writer = new StringWriter();
        
        writer.append("{ \"success\": true, \"message\":\"FAMS entry saved.\"}");
        LOG.debug(writer.toString());
        responder.append(writer.toString());
      }
    }
    else
    {
      LOG.info("Unauthorized request.");
      response.sendError(401, "Unauthorized");
    }
  }
}
