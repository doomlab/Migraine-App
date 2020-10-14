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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/user/profile/*")
public class UserProfileServlet extends HttpServlet {
  private static final long   serialVersionUID = 1L;
  private static final Logger LOG              = LogManager.getLogger("MIGRAINE");
  private Gson                gson;

  @Override
  public void init()
  {
    LOG.debug("User profile servlet initialized.");
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
        LOG.debug("UserProfileServlet (POST): " + pathInfo);

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
    LOG.debug("user profile: " + data);

    String userId = null;
    if (null != data)
    {
      UserHeadProfileRequest re = gson.fromJson(data, UserHeadProfileRequest.class);
      userId = re.getUserId();
    }
    if (null == userId)
    {
      LOG.debug("Invalid user profile request: missing user id");
      response.sendError(400, "Invalid request.");
      return;
    }
    user = User.getById(UUID.fromString(userId));
    if (null == user)
    {
      LOG.debug("Invalid user profile request: User id not found.");
      response.sendError(400, "Invalid request.");
      return;
    }
    UserHeadProfile rep = new UserHeadProfile();
    rep.setUserId(userId);
    rep.setDiagnosed(user.getDiagnosed());
    if (user.getDiagnosed())
    {
      rep.setDiagnosis(user.getDiagnosis());
      if (null != user.getDiagnosisDate())
      {
        rep.setDiagnosisYear(Integer.toString(user.getDiagnosisDate().toLocalDateTime().getYear()));
      }
    }
    if (null != user.getStart())
    {
      rep.setStartYear(Integer.toString(user.getStart().toLocalDateTime().getYear()));
    }
    // Add user chronic medications

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
    UserHeadProfile re     = null;
    if (null != data)
    {
      re = gson.fromJson(data, UserHeadProfile.class);
      userId = re.getUserId();
    }
    if (null == userId)
    {
      LOG.debug("Invalid user profile request: missing user id");
      response.sendError(400, "Invalid request.");
      return;
    }
    user = User.getById(UUID.fromString(userId));
    if (null == user)
    {
      LOG.debug("Invalid user profile request: User id not found.");
      response.sendError(400, "Invalid request.");
      return;
    }
    user.setDiagnosed(re.isDiagnosed());
    if (re.isDiagnosed())
    {
      if (null != re.getDiagnosisYear())
      {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(re.getDiagnosisYear()), 0, 0);
        user.setDiagnosisDate(Timestamp.from(cal.toInstant()));
      }
      user.setDiagnosis(re.getDiagnosis());
    }
    if (null != re.getStartYear())
    {
      Calendar cal = Calendar.getInstance();
      cal.set(Integer.parseInt(re.getStartYear()), 0, 0);
      user.setDiagnosisDate(Timestamp.from(cal.toInstant()));
    }

    // Also save medication list

    try
    {
      user.setModified(Timestamp.valueOf(LocalDateTime.now()));
      User.update(user);

      UserHeadProfileSaveResponse rep = new UserHeadProfileSaveResponse();
      rep.setSuccess(true);
      rep.setMessage("User profile updated.");

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
      LOG.error("Error saving user profile", e);
      response.sendError(500, "Error saving user profile");
    }

  }

  static class UserHeadProfileRequest {
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

  static class UserHeadProfile {
    protected String  userId;
    public boolean    diagnosed;
    public String     diagnosisYear;
    public String     diagnosis;
    public String     startYear;
    public List<Long> medications;

    public String getUserId()
    {
      return userId;
    }

    public void setUserId(String userId)
    {
      this.userId = userId;
    }

    public boolean isDiagnosed()
    {
      return diagnosed;
    }

    public void setDiagnosed(boolean diagnosed)
    {
      this.diagnosed = diagnosed;
    }

    public String getDiagnosisYear()
    {
      return diagnosisYear;
    }

    public void setDiagnosisYear(String diagnosisYear)
    {
      this.diagnosisYear = diagnosisYear;
    }

    public String getDiagnosis()
    {
      return diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
      this.diagnosis = diagnosis;
    }

    public String getStartYear()
    {
      return startYear;
    }

    public void setStartYear(String startYear)
    {
      this.startYear = startYear;
    }

    public List<Long> getMedications()
    {
      return medications;
    }

    public void setMedications(List<Long> medications)
    {
      this.medications = medications;
    }

  }

  static class UserHeadProfileSaveResponse {
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
