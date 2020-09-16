package com.clinvest.migraine.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.clinvest.migraine.server.data.DiaryMedicationEntry;
import com.clinvest.migraine.server.data.Medication;
import com.clinvest.migraine.server.data.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/diary")
public class DiaryServlet extends HttpServlet {
  private static final long   serialVersionUID = 1L;
  private static final Logger LOG              = LogManager.getLogger("MIGRAINE");
  private Gson                gson;

  @Override
  public void init()
  {
    gson = new GsonBuilder().setLenient().create();
    LOG.debug("Diary servlet initialized.");
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
      LOG.debug("Diary save request: " + data);
      if (null != data)
      {
        DiarySaveRequest dsr = gson.fromJson(data, DiarySaveRequest.class);

        user = User.getById(UUID.fromString(dsr.getUserId()));
        if (null == user)
        {
          LOG.error("Invalid diary servlet request: User ID not found.");
          response.sendError(400, "Invalid request: User ID not found.");
          return;
        }
        DiaryEntry de = new DiaryEntry();
        dsr.populate(de, user);
        DiaryEntry.save(de);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // create HTML response
        PrintWriter  responder = response.getWriter();
        StringWriter writer    = new StringWriter();

        DiarySaveResponse re = new DiarySaveResponse();
        re.setSuccess(true);
        re.setMessage("Diary entry saved.");
        writer.append(gson.toJson(re));
        LOG.debug(writer.toString());
        responder.append(writer.toString());
      }
    } else
    {
      LOG.info("Unauthorized request.");
      response.sendError(401, "Unauthorized");
    }
  }
  
  static class DiaryMedication
  {
    long medicationId;
    String howOften;
    boolean painDecrease;
    public long getMedicationId()
    {
      return medicationId;
    }
    public void setMedicationId(long medicationId)
    {
      this.medicationId = medicationId;
    }
    public String getHowOften()
    {
      return howOften;
    }
    public void setHowOften(String howOften)
    {
      this.howOften = howOften;
    }
    public boolean isPainDecrease()
    {
      return painDecrease;
    }
    public void setPainDecrease(boolean painDecrease)
    {
      this.painDecrease = painDecrease;
    }
    
    
  }
  
  static class DiarySaveRequest {
    public String     userId;
    public String     severity;
    public boolean    newHeadache;
    public double     hours;
    public boolean    painDirectional;
    public boolean    painThrobbing;
    public boolean    painWorse;
    public boolean    nausea;
    public boolean    lightSensitive;
    public boolean    soundSensitive;
    public String     worstSymptom;
    public boolean    tookMedication;
    public List<DiaryMedication> medications;
  
    public String getUserId()
    {
      return userId;
    }

    public void setUserId(String userId)
    {
      this.userId = userId;
    }

    public String getSeverity()
    {
      return severity;
    }

    public void setSeverity(String severity)
    {
      this.severity = severity;
    }

    public boolean isNewHeadache()
    {
      return newHeadache;
    }

    public void setNewHeadache(boolean newHeadache)
    {
      this.newHeadache = newHeadache;
    }

    public double getHours()
    {
      return hours;
    }

    public void setHours(double hours)
    {
      this.hours = hours;
    }

    public boolean isPainDirectional()
    {
      return painDirectional;
    }

    public void setPainDirectional(boolean painDirectional)
    {
      this.painDirectional = painDirectional;
    }

    public boolean isPainThrobbing()
    {
      return painThrobbing;
    }

    public void setPainThrobbing(boolean painThrobbing)
    {
      this.painThrobbing = painThrobbing;
    }

    public boolean isPainWorse()
    {
      return painWorse;
    }

    public void setPainWorse(boolean painWorse)
    {
      this.painWorse = painWorse;
    }

    public boolean isNausea()
    {
      return nausea;
    }

    public void setNausea(boolean nausea)
    {
      this.nausea = nausea;
    }

    public boolean isLightSensitive()
    {
      return lightSensitive;
    }

    public void setLightSensitive(boolean lightSensitive)
    {
      this.lightSensitive = lightSensitive;
    }

    public boolean isSoundSensitive()
    {
      return soundSensitive;
    }

    public void setSoundSensitive(boolean soundSensitive)
    {
      this.soundSensitive = soundSensitive;
    }

    public String getWorstSymptom()
    {
      return worstSymptom;
    }

    public void setWorstSymptom(String worstSymptom)
    {
      this.worstSymptom = worstSymptom;
    }

    public boolean isTookMedication()
    {
      return tookMedication;
    }

    public void setTookMedication(boolean tookMedication)
    {
      this.tookMedication = tookMedication;
    }

    public List<DiaryMedication> getMedications()
    {
      return medications;
    }

    public void setMedications(List<DiaryMedication> medications)
    {
      this.medications = medications;
    }

    public void populate(DiaryEntry de, User u)
    {
      de.setUser(u);
      de.setStudy(u.getStudy());
      de.setCreated(Timestamp.valueOf(LocalDateTime.now()));
      de.setEntryTimestamp(de.getCreated());
      de.setSeverity(severity);
      de.setNewHeadache(newHeadache);
      de.setHours(hours);
      de.setPainDirectional(painDirectional);
      de.setPainThrobbing(painThrobbing);
      de.setPainWorse(painWorse);
      de.setNausea(nausea);
      de.setLightSensitive(lightSensitive);
      de.setSoundSensitive(soundSensitive);
      de.setWorstSymptom(worstSymptom);
      de.setTookMedication(tookMedication);
      if (null != medications && medications.size() > 0)
      {
        Set<DiaryMedicationEntry> meds = new HashSet<DiaryMedicationEntry>();
        for (DiaryMedication l : medications)
        {
          Medication m = Medication.getById(l.getMedicationId());
          if (null == m)
          {
            LOG.error("Invalid medication id in diary save request: " + m);
          }
          else
          {
            DiaryMedicationEntry dme = new DiaryMedicationEntry();
            dme.setEntry(de);
            dme.setCreated(de.getCreated());
            dme.setMedication(m);
            dme.setHowOften(l.getHowOften());
            dme.setPainDecrease(l.isPainDecrease());
            meds.add(dme);
          }
        }
        if (meds.size() > 0)
        {
          de.setMedications(meds);
        }
      }
      
    }
  }

  public class DiarySaveResponse {
    public boolean success;
    public String  message;

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
