package com.clinvest.migraine.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
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
import com.clinvest.migraine.server.data.UserConsent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/user/consent/*")
public class ConsentServlet extends HttpServlet {
  private static final long   serialVersionUID = 1L;
  private static final Logger LOG              = LogManager.getLogger("MIGRAINE");
  private Gson                gson;

  @Override
  public void init()
  {
    LOG.debug("User consent servlet initialized.");
    gson = new GsonBuilder().setLenient().create();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    String authToken = request.getParameter("auth");
    // Check auth token
    if (null != authToken && Authorize.checkAuthToken(authToken))
    {
      String pathInfo = request.getPathInfo();
      if (null != pathInfo)
      {
        LOG.debug("UserConsentServlet (POST): " + pathInfo);

        if (pathInfo.contains("get"))
        {
          get(request, response);
        } else if (pathInfo.contains("save"))
        {
          save(request, response);
        } else
        {
          LOG.debug("Invalid request.");
          response.sendError(400, "Invalid request.");
        }

      } else
      {
        LOG.debug("Invalid request.");
        response.sendError(400, "Invalid request.");
      }
    } else
    {
      LOG.info("Unauthorized request.");
      response.sendError(401, "Unauthorized");
    }

  }

  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    User           user   = null;

    BufferedReader reader = request.getReader();
    String         data   = IOUtils.toString(reader);
    LOG.debug("user consent: " + data);

    String userId = null;
    if (null != data)
    {
      UserConsentRequest re = gson.fromJson(data, UserConsentRequest.class);
      userId = re.getUserId();
    }
    if (null == userId)
    {
      LOG.debug("Invalid user consent request: missing user id");
      response.sendError(400, "Invalid request.");
      return;
    }
    user = User.getById(UUID.fromString(userId));
    if (null == user)
    {
      LOG.debug("Invalid user consent request: User id not found.");
      response.sendError(400, "Invalid request.");
      return;
    }
    UserConsentResponse rep = new UserConsentResponse();
    rep.setUserId(userId);

    List<UserConsent> uc = UserConsent.getByUserId(user.getId());
    if (null != uc && uc.size() > 0)
    {
      rep.setConsent(uc.get(0).getConsent());
    }

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    // create HTML response
    PrintWriter  responder = response.getWriter();
    StringWriter writer    = new StringWriter();

    writer.append(gson.toJson(rep));
    LOG.debug(writer.toString());
    responder.append(writer.toString());

  }

  protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    User           user   = null;

    BufferedReader reader = request.getReader();
    String         data   = IOUtils.toString(reader);
    LOG.debug("user profile: " + data);

    String          userId = null;
    UserConsentResponse re = null;
    if (null != data)
    {
      re = gson.fromJson(data, UserConsentResponse.class);
      userId = re.getUserId();
    }
    if (null == userId)
    {
      LOG.debug("Invalid user consent request: missing user id");
      response.sendError(400, "Invalid request.");
      return;
    }
    user = User.getById(UUID.fromString(userId));
    if (null == user)
    {
      LOG.debug("Invalid user consent request: User id not found.");
      response.sendError(400, "Invalid request.");
      return;
    }
    

    try
    {
      UserConsent uc = new UserConsent();
      uc.setUser(user);
      uc.setConsent(re.getConsent());
      uc.setConsentDate(Timestamp.valueOf(LocalDateTime.now()));
      UserConsent.save(uc);

      UserConsentSaveResponse rep = new UserConsentSaveResponse();
      rep.setSuccess(true);
      rep.setMessage("User consent updated.");

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      // create HTML response
      PrintWriter  responder = response.getWriter();
      StringWriter writer    = new StringWriter();

      writer.append(gson.toJson(rep));
      LOG.debug(writer.toString());
      responder.append(writer.toString());
    } catch (Exception e)
    {
      LOG.error("Error saving user consent", e);
      response.sendError(500, "Error saving user consent");
    }

  }

  static class UserConsentRequest {
    String userId;

    public String getUserId()
    {
      return userId;
    }

    public void setUserId(String userId)
    {
      this.userId = userId;
    }
  }

  static class UserConsentResponse {
    protected String  userId;
    public boolean    consent;

    public String getUserId()
    {
      return userId;
    }

    public void setUserId(String userId)
    {
      this.userId = userId;
    }

    public boolean getConsent()
    {
      return consent;
    }

    public void setConsent(boolean consent)
    {
      this.consent = consent;
    }
  }

  static class UserConsentSaveResponse {
    protected boolean success;
    protected String  message;

    public boolean isSuccess()
    {
      return success;
    }

    public void setSuccess(boolean success)
    {
      this.success = success;
    }

    public String getMessage()
    {
      return message;
    }

    public void setMessage(String message)
    {
      this.message = message;
    }

  }
}
