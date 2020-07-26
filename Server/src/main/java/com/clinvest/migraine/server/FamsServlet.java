package com.clinvest.migraine.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

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
        FamsEntryRequest fer = gson.fromJson(data, FamsEntryRequest.class);

        user = User.getById(UUID.fromString(fer.getUserId()));
        if (null == user)
        {
          LOG.error("Invalid user servlet request: User ID not found.");
          response.sendError(400, "Invalid request: User ID not found.");
          return;
        }
        FamsEntry fe = new FamsEntry();
        fer.populate(fe, user);
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
  
  static class FamsEntryRequest
  {
    protected String userId;
    protected Long   studyId;
    protected Integer Q1;
    protected Integer Q2;
    protected Integer Q3;
    protected Integer Q4;
    protected Integer Q5;
    protected Integer Q6;
    protected Integer Q7;
    protected Integer Q8;
    protected Integer Q9;
    protected Integer Q10;
    protected Integer Q11;
    protected Integer Q12;
    protected Integer Q13;
    protected Integer Q14;
    protected Integer Q15;
    protected Integer Q16;
    protected Integer Q17;
    protected Integer Q18;
    protected Integer Q19;
    protected Integer Q20;
    protected Integer Q21;
    protected Integer Q22;
    protected Integer Q23;
    protected Integer Q24;
    protected Integer Q25;
    protected Integer Q26;
    protected Integer Q27;
    public String getUserId()
    {
      return userId;
    }
    public void setUserId(String userId)
    {
      this.userId = userId;
    }
    public Long getStudyId()
    {
      return studyId;
    }
    public void setStudyId(Long studyId)
    {
      this.studyId = studyId;
    }
    public Integer getQ1()
    {
      return Q1;
    }
    public void setQ1(Integer q1)
    {
      Q1 = q1;
    }
    public Integer getQ2()
    {
      return Q2;
    }
    public void setQ2(Integer q2)
    {
      Q2 = q2;
    }
    public Integer getQ3()
    {
      return Q3;
    }
    public void setQ3(Integer q3)
    {
      Q3 = q3;
    }
    public Integer getQ4()
    {
      return Q4;
    }
    public void setQ4(Integer q4)
    {
      Q4 = q4;
    }
    public Integer getQ5()
    {
      return Q5;
    }
    public void setQ5(Integer q5)
    {
      Q5 = q5;
    }
    public Integer getQ6()
    {
      return Q6;
    }
    public void setQ6(Integer q6)
    {
      Q6 = q6;
    }
    public Integer getQ7()
    {
      return Q7;
    }
    public void setQ7(Integer q7)
    {
      Q7 = q7;
    }
    public Integer getQ8()
    {
      return Q8;
    }
    public void setQ8(Integer q8)
    {
      Q8 = q8;
    }
    public Integer getQ9()
    {
      return Q9;
    }
    public void setQ9(Integer q9)
    {
      Q9 = q9;
    }
    public Integer getQ10()
    {
      return Q10;
    }
    public void setQ10(Integer q10)
    {
      Q10 = q10;
    }
    public Integer getQ11()
    {
      return Q11;
    }
    public void setQ11(Integer q11)
    {
      Q11 = q11;
    }
    public Integer getQ12()
    {
      return Q12;
    }
    public void setQ12(Integer q12)
    {
      Q12 = q12;
    }
    public Integer getQ13()
    {
      return Q13;
    }
    public void setQ13(Integer q13)
    {
      Q13 = q13;
    }
    public Integer getQ14()
    {
      return Q14;
    }
    public void setQ14(Integer q14)
    {
      Q14 = q14;
    }
    public Integer getQ15()
    {
      return Q15;
    }
    public void setQ15(Integer q15)
    {
      Q15 = q15;
    }
    public Integer getQ16()
    {
      return Q16;
    }
    public void setQ16(Integer q16)
    {
      Q16 = q16;
    }
    public Integer getQ17()
    {
      return Q17;
    }
    public void setQ17(Integer q17)
    {
      Q17 = q17;
    }
    public Integer getQ18()
    {
      return Q18;
    }
    public void setQ18(Integer q18)
    {
      Q18 = q18;
    }
    public Integer getQ19()
    {
      return Q19;
    }
    public void setQ19(Integer q19)
    {
      Q19 = q19;
    }
    public Integer getQ20()
    {
      return Q20;
    }
    public void setQ20(Integer q20)
    {
      Q20 = q20;
    }
    public Integer getQ21()
    {
      return Q21;
    }
    public void setQ21(Integer q21)
    {
      Q21 = q21;
    }
    public Integer getQ22()
    {
      return Q22;
    }
    public void setQ22(Integer q22)
    {
      Q22 = q22;
    }
    public Integer getQ23()
    {
      return Q23;
    }
    public void setQ23(Integer q23)
    {
      Q23 = q23;
    }
    public Integer getQ24()
    {
      return Q24;
    }
    public void setQ24(Integer q24)
    {
      Q24 = q24;
    }
    public Integer getQ25()
    {
      return Q25;
    }
    public void setQ25(Integer q25)
    {
      Q25 = q25;
    }
    public Integer getQ26()
    {
      return Q26;
    }
    public void setQ26(Integer q26)
    {
      Q26 = q26;
    }
    public Integer getQ27()
    {
      return Q27;
    }
    public void setQ27(Integer q27)
    {
      Q27 = q27;
    }
    
    public void populate(FamsEntry fe, User u)
    {
      fe.setUser(u);
      fe.setQ1(Q1);
      fe.setQ2(Q2);
      fe.setQ3(Q3);
      fe.setQ4(Q4);
      fe.setQ5(Q5);
      fe.setQ6(Q6);
      fe.setQ7(Q7);
      fe.setQ8(Q8);
      fe.setQ9(Q9);
      fe.setQ10(Q10);
      fe.setQ11(Q11);
      fe.setQ12(Q12);
      fe.setQ13(Q13);
      fe.setQ14(Q14);
      fe.setQ15(Q15);
      fe.setQ16(Q16);
      fe.setQ17(Q17);
      fe.setQ18(Q18);
      fe.setQ19(Q19);
      fe.setQ20(Q20);
      fe.setQ21(Q21);
      fe.setQ22(Q22);
      fe.setQ23(Q23);
      fe.setQ24(Q24);
      fe.setQ25(Q25);
      fe.setQ26(Q26);
      fe.setQ27(Q27);
    }
  }
}
