package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.digest.UnixCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Database
{
  private static final Logger LOG = LogManager.getLogger("MIGRAINE");
  private static Database instance;
  private Gson gson;

  /** Default root user for the NUA application. */
  private static final String[][] USERS = { { "ef8ed6b2-9d4f-43c3-b253-ef4d2eb544f5", "Admin", "User",
      "migraine-app@clinvest.com", "p@$$w0rd","confirm" } };
  
  /** Default roles for users of the NUA application. */
  private static final String[][] ROLES = {
      {"admin", "Application and Database Administrator"},
      {"researcher", "Non-admin research user."},
      {"user", "Application End User"}
  };
  
  /** Assign the default root user the default admin role */
  private static final String[][] USER_ROLES = {
      {  "ef8ed6b2-9d4f-43c3-b253-ef4d2eb544f5", "admin" }
  };
  
  private static final String[][] MEDICATIONS = {
      {  "Tylenol", "acetaminophen" },
      {  "Motrin",  "ibuprophen" },
      {  "Aleve",   "naproxen sodium" }
  };

  private static final String[] STUDIES = {
    "{ \"name\": \"Development and Test Study\", \"description\": \"Study to use during application development and testing.\", \"sponsor\": \"Harrisburg University\", " + 
    "  \"investigator\": \"Phil Grim\", \"contact\": \"pgrim@harrisburgu.edu\", \"startDate\": \"Jan 15, 2020 12:00:00 AM\",  \"endDate\": \"Dec 15, 2020 12:00:00 AM\" }", 
    "{ \"name\": \"Migraine Scale Study\", \"description\": \"Study to test migraine scales.\", \"sponsor\": \"Clinvest\", " + 
    "  \"investigator\": \"Dr. Erin Buchanan\", \"contact\": \"ebuchanan@harrisburgu.edu\", \"startDate\": \"Aug 15, 2020 12:00:00 AM\",  \"endDate\": \"Jul 15, 2021 12:00:00 AM\" }" 
  };
  
  public static void initialize()
  {
    if (null == instance)
    {
      instance = new Database();
    }
  }

  private Database()
  {
    gson = new GsonBuilder().setLenient().create();
    // Create the session factory, which will ensure tables get created.
    LOG.debug("Checking for database tables.");
    HibernateUtils.getSessionFactory();
    // Check to see if the root user exists. If not, create all of the standard users and roles.
    LOG.debug("Checking for standard data.");
    User root = null;
    try
    {
      root = User.getById(UUID.fromString(USERS[0][0]));
    }
    catch (Exception e)
    {
      LOG.debug("No database detected.");
    }
    if (null == root)
    {
      createUsers();
      createRoles();
      createUserRoles();
      createStudies();
    }
    updateMedicationList();
  }

  private void createUsers()
  {
    for (int i = 0; i < USERS.length; i++)
    {
      String[] data = USERS[i];
      User u = new User();
      u.setId(UUID.fromString(data[0]));
      u.setFirstName(data[1]);
      u.setLastName(data[2]);
      u.setEmail(data[3]);
      u.setBirthDate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
      u.setPassword(UnixCrypt.crypt(data[4], "MA"));

      if ("confirm".equals(data[5]))
      {
        u.setConfirmed(Timestamp.valueOf(LocalDateTime.now()));
      }

      User.save(u);
    }
    LOG.debug("Users created.");
  }
  
  private void createRoles()
  {
    for (int i = 0; i < ROLES.length; i++)
    {
      String[] data = ROLES[i];
      Role r = new Role();
      r.setName(data[0]);
      r.setDescription(data[1]);
      
      Role.save(r);
    }
    LOG.debug("Roles created.");
  }
  
  private void createUserRoles()
  {
    for (int i = 0; i < USER_ROLES.length; i++)
    {
      String[] data = USER_ROLES[i];
      UserRole ur = new UserRole();
      User u = User.getById(UUID.fromString(data[0]));
      ur.setUser(u);

      Role r = Role.getByName(data[1]);
      if (null != r)
      {
        ur.setRoleId(r.getId());
        
        UserRole.save(ur);
      }
      else
      {
        LOG.error("Error creating user role:  Role not found.");
      }
    }
    LOG.debug("User roles created.");
  }
  
  private void createStudies()
  {
    for (int i = 0; i < STUDIES.length; i++)
    {
      String data = STUDIES[i];
      Study s = gson.fromJson(data, Study.class);
      
      Study.save(s);
    }
    LOG.debug("Studies created.");
  }
  
  private void updateMedicationList()
  {
    List<Medication> meds = Medication.list();
    Set<String> names = new HashSet<String>();
    if (null != meds)
    {
      for (Medication m : meds)
      {
        names.add(m.getName());
      }
    }
    for (int i = 0; i < MEDICATIONS.length; i++)
    {
      String[] data = MEDICATIONS[i];
      if (!names.contains(data[0]))
      {
        Medication m = new Medication();
        m.setName(data[0]);
        m.setFormulary(data[1]);

        Medication.save(m);
      }
    }
    LOG.debug("Medication list updated.");
  }


  // Test the database creation
  public static void main(String[] args)
  {
    Database.initialize();
  }
}
