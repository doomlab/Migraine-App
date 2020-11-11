package com.clinvest.migraine.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

import com.clinvest.migraine.server.data.DiaryEntry;
import com.clinvest.migraine.server.data.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/headache/*")
public class HeadacheServlet extends HttpServlet {
  
  private static final long   serialVersionUID = 1L;
  private static final Logger LOG              = LogManager.getLogger("MIGRAINE");
  private Gson                gson;
  
  @Override
  public void init()
  {
    LOG.debug("Headache servlet initialized.");
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
        LOG.debug("HeadacheServlet (POST): " + pathInfo);

        if (pathInfo.contains("get_days"))
        {
          getDays(request, response);
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
  
  protected void getDays(HttpServletRequest request, HttpServletResponse response) throws IOException
  {
    User           user   = null;

    BufferedReader reader = request.getReader();
    String         data   = IOUtils.toString(reader);
    LOG.debug("getDays: " + data);

    String userId = null;
    if (null != data)
    {
      HeadacheDaysRequest re = gson.fromJson(data, HeadacheDaysRequest.class);
      userId = re.getUserId();
    }
    if (null == userId)
    {
      LOG.debug("Invalid headache days request: missing user id");
      response.sendError(400, "Invalid request.");
      return;
    }
    user = User.getById(UUID.fromString(userId));
    if (null == user)
    {
      LOG.debug("Invalid headache days request: User id not found.");
      response.sendError(400, "Invalid request.");
      return;
    }
    
    List<DiaryEntry> entries = DiaryEntry.getLast30Days(user);
    HeadacheDaysResponse rep = new HeadacheDaysResponse();
    int NUM_DAYS = 28; // We're doing 28 days on screen.
    int[] theDays = new int[NUM_DAYS]; 
    if (null != entries && entries.size() > 0)
    {    
      int dayIndex = todayIndex() - 1;
      if (dayIndex < 0)
      {
        dayIndex = 27;
      }
      else
      {
        dayIndex += 21;
      }
      // Everything past dayIndex is a 0
      for (int i = dayIndex + 1; i < NUM_DAYS; i++)
      {
        theDays[i] = 0;
      }
      
      Calendar cal = new GregorianCalendar();
      Calendar cell = new GregorianCalendar();

      cal.add(Calendar.DAY_OF_YEAR, -1); // Yesterday
      while (dayIndex >= 0)
      {
        boolean found = false;
        for (DiaryEntry ent : entries)
        {
          cell.setTime(ent.getEntryTimestamp());
          if (sameDay(cell, cal))
          {
            String level = ent.getSeverity();
            if ("Mild Pain".equalsIgnoreCase(level))
            {
              theDays[dayIndex] = 1;
            }
            else if ("Moderate Pain".equalsIgnoreCase(level))
            {
              theDays[dayIndex] = 2;
            }
            else if ("Severe Pain".equalsIgnoreCase(level))
            {
              theDays[dayIndex] = 3;
            }
            else
            {
              theDays[dayIndex] = 0;
            }
            found = true;
            break;
          }
          if (found)
          {
            break;
          }
        }
        if (!found)
        {
          theDays[dayIndex] = 0;
        }
        dayIndex -= 1;
        cal.add(Calendar.DAY_OF_YEAR, -1);       
      }
    }
    else
    {
      for (int i = 0; i < NUM_DAYS; i++)
      {
        theDays[i] = 0;
      }
    }
    List<Integer> repDays = new ArrayList<Integer>();
    for (int i = 0; i < NUM_DAYS; i++)
    {
      repDays.add(theDays[i]);
    }
    rep.setUserId(userId);
    rep.setHeadacheDays(repDays);
    
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    // create HTML response
    PrintWriter  responder = response.getWriter();
    StringWriter writer    = new StringWriter();

    writer.append(gson.toJson(rep));
    LOG.debug(writer.toString());
    responder.append(writer.toString());

  }

  protected int todayIndex()
  {
    Calendar cal = new GregorianCalendar();
    return cal.get(Calendar.DAY_OF_WEEK);
  }
  
  protected boolean sameDay(Calendar cal1, Calendar cal2)
  {
    return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
  }

  static class HeadacheDaysRequest
  {
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
  
  static class HeadacheDaysResponse
  {
      String userId;
      List<Integer> headacheDays;

      public List<Integer> getHeadacheDays()
      {
        return headacheDays;
      }

      public void setHeadacheDays(List<Integer> headacheDays)
      {
        this.headacheDays = headacheDays;
      }
      
      public String getUserId()
      {
        return userId;
      }

      public void setUserId(String userId)
      {
        this.userId = userId;
      }
      
  }
}
