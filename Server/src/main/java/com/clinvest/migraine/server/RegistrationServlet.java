package com.clinvest.migraine.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.clinvest.migraine.server.data.User;



@WebServlet("/register")
public class RegistrationServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger("NUA");
  protected Properties props;

  @Override
  public void init()
  {
    props = new Properties();
    try
    {
      props.load(RegistrationServlet.class.getClassLoader().getResourceAsStream("migraine.properties"));
    }
    catch (IOException e)
    {
      LOG.error("Error loading properties file: ", e);
      throw new RuntimeException(e);
    }
    LOG.debug("Registration servlet initialized.");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean success = true;
    String firstName = null;
    String lastName = null;
    Timestamp birthDate = null;
    String email = null;
    String password = null;
    Timestamp created = Timestamp.valueOf(LocalDateTime.now());

    BufferedReader reader = request.getReader();
    String data = IOUtils.toString(reader);
    LOG.debug("registration: " + data);
    if (null != data)
    {
      JSONObject dataJsonObject = new JSONObject(data);

      if (dataJsonObject.has("firstName"))
      {
        firstName = dataJsonObject.getString("firstName").trim();
      }
      if (dataJsonObject.has("lastName"))
      {
        lastName = dataJsonObject.getString("lastName").trim();
      }
      if (dataJsonObject.has("password"))
      {
        password = dataJsonObject.getString("password").trim();
      }
      if (dataJsonObject.has("email"))
      {
        email = dataJsonObject.getString("email").trim();
      }
      if (dataJsonObject.has("birthDate"))
      {
        birthDate = new Timestamp(dataJsonObject.getLong("birthDate"));
      }
    }
    
    // This should be checked at the client end, but let's sanity check it here.
    if (null == firstName || null == lastName || null == password || null == email || null == birthDate)
    {
      LOG.debug("Invalid registration request: missing required data");
      response.sendError(400, "Invalid request: missing required data");
      success = false;
    }
    if (success)
    {
      User userRecord = User.getByEmail(email);
      if (null != userRecord)
      {
        LOG.debug("Invalid registration request: E-mail already registered");
        response.sendError(400, "Invalid request: E-mail already registered");
        success = false;
      }
    }
    if (success)
    {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      // create HTML response
      PrintWriter responder = response.getWriter();
      StringWriter writer = new StringWriter();

      User newUser = new User();
      newUser.setId(UUID.randomUUID());
      newUser.setFirstName(firstName);
      newUser.setLastName(lastName);
      newUser.setEmail(email);
      newUser.setBirthDate(birthDate);
      newUser.setPassword(password);
      newUser.setCreated(created);
      User.save(newUser);
      
      // Send a confirmation email.
      // Setup mail session
      Properties properties = System.getProperties();
      properties.setProperty("mail.smtp.host", props.getProperty("nua.mail.outbound"));
      Session session = Session.getDefaultInstance(properties,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(props.getProperty("nua.mail.login"), 
                                                 props.getProperty("nua.mail.password"));
            }
          }
      );
      try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(props.getProperty("nua.mail.from")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(newUser.getEmail()));
        message.setSubject(confirmationEmailSubject);
        message.setText(String.format(confirmationEmailBody, newUser.getFirstName(), props.get("nua.server.url"), newUser.getId().toString()));
        // Send message
        Transport.send(message);
        LOG.debug("Sent confirmation message successfully.");
     } catch (MessagingException mex) {
        LOG.error("Error sending confirmation email: ", mex);
     }
      
      
      writer.append("{ \"success\": true }");
      LOG.debug(writer.toString());
      responder.append(writer.toString());
    }
  }
  
  protected static final String confirmationEmailSubject = "Welcome to the Migraine App!";
  protected static final String confirmationEmailBody = "Dear %s,\n\nWelcome to the Migraine App! In order to confirm your registration, please click in the link below.\n\n" +
    "http://%s/nua/register?id=%s\n\n";
}
