package com.clinvest.migraine.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.clinvest.migraine.server.data.User;
import com.clinvest.migraine.server.data.UserSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger("MIGRAINE");

  private Gson gson;

  @Override
  public void init()
  {
    gson = new GsonBuilder().setLenient().create();
    LOG.debug("Login servlet initialized.");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean success = true;
    String user = null;
    String pass = null;
    
    BufferedReader reader = request.getReader();
    String data = IOUtils.toString(reader);
    LOG.debug("login: " + data);
    if (null != data)
    {
      LoginRequest lr = gson.fromJson(data, LoginRequest.class);

      user = lr.getUsername();
      pass = lr.getPassword();
    }
    if (null == user || null == pass) 
    {
      LOG.debug("Invalid login request.");
      response.sendError(400, "Invalid request.");
      success = false;
    }
    if (success)
    {
      User userRecord = User.getByEmail(user);
      if (null == userRecord)
      {
        LOG.debug("Invalid user.");
        success = false;
      }

      if (success)
      {
        if (null == pass || !pass.equals(userRecord.getPassword()))
        {
          LOG.debug("Incorrect password.");
          success = false;
        }
      }

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      // create HTML response
      PrintWriter responder = response.getWriter();
      StringWriter writer = new StringWriter();

      if (success)
      {
        UserSession s = new UserSession();
        s.setSessionId(UUID.randomUUID());
        s.setUserId(userRecord.getId());
        s.setActive(Timestamp.valueOf(LocalDateTime.now()));
        UserSession.save(s);
        
        LoginResponse r = new LoginResponse();
        r.setUserId(userRecord.getId().toString());
        r.setAuthToken(s.getSessionId().toString());

        writer.append(gson.toJson(r));
        LOG.debug(writer.toString());
        responder.append(writer.toString());
      }
      else
      {
        LOG.debug("Invalid login attempt.");
        response.sendError(401, "Invalid login.");
      }
    }
  }

  static class LoginRequest
  {
    protected String username;
    protected String password;

    public String getUsername()
    {
      return username;
    }

    public void setUsername(String username)
    {
      this.username = username;
    }

    public String getPassword()
    {
      return password;
    }

    public void setPassword(String password)
    {
      this.password = password;
    }
  }

  static class LoginResponse
  {
    protected String userId;
    protected String authToken;

    public String getUserId()
    {
      return userId;
    }

    public void setUserId(String userId)
    {
      this.userId = userId;
    }

    public String getAuthToken()
    {
      return authToken;
    }

    public void setAuthToken(String authToken)
    {
      this.authToken = authToken;
    }
  }
}
