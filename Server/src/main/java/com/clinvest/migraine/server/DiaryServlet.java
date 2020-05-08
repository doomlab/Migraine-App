package com.clinvest.migraine.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

@WebServlet("/diary")
public class DiaryServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger("MIGRAINE");

  @Override
  public void init()
  {
    LOG.debug("Login servlet initialized.");
  }

}
