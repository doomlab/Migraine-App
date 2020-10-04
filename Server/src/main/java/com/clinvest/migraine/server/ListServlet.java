package com.clinvest.migraine.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.clinvest.migraine.server.data.Medication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/list/*")
public class ListServlet extends HttpServlet {
  private static final long   serialVersionUID = 1L;
  private static final Logger LOG              = LogManager.getLogger("MIGRAINE");
  private Gson                gson;

  @Override
  public void init()
  {
    LOG.debug("List servlet initialized.");
    gson = new GsonBuilder().setLenient().create();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String pathInfo = request.getPathInfo();
    if (null != pathInfo)
    {
      LOG.debug("ListServlet (GET): " + pathInfo);
      String[] parts = pathInfo.split("/");
      if (parts != null && parts.length > 1)
      {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // create HTML response
        PrintWriter      responder = response.getWriter();
        StringWriter     writer    = new StringWriter();

        List<Medication> meds      = null;

        if ("acute".equalsIgnoreCase(parts[parts.length - 1]))
        {
          meds = Medication.listAcute();
        } else if ("preventative".equalsIgnoreCase(parts[parts.length - 1]))
        {
          meds = Medication.listPreventative();
        } else
        {
          LOG.debug("Invalid request.");
          response.sendError(400, "Invalid request.");
        }

        if (null != meds)
        {
          MedicationListResponse resp = new MedicationListResponse();
          resp.setMeds(meds);
          writer.append(gson.toJson(resp));
        }

        LOG.debug(writer.toString());
        responder.append(writer.toString());
      }
    } else
    {
      LOG.debug("Invalid request.");
      response.sendError(400, "Invalid request.");
    }

  }
  
  public static class MedicationListResponse
  {
    protected List<Medication> meds;

    public List<Medication> getMeds()
    {
      return meds;
    }

    public void setMeds(List<Medication> meds)
    {
      this.meds = meds;
    }
    
  }
}
