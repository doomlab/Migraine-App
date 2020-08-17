package com.clinvest.migraine.simulation;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.UnixCrypt;

import com.clinvest.migraine.server.data.Database;
import com.clinvest.migraine.server.data.DiaryEntry;
import com.clinvest.migraine.server.data.DiaryMedicationEntry;
import com.clinvest.migraine.server.data.FamsEntry;
import com.clinvest.migraine.server.data.Medication;
import com.clinvest.migraine.server.data.Role;
import com.clinvest.migraine.server.data.Study;
import com.clinvest.migraine.server.data.User;
import com.clinvest.migraine.server.data.UserConsent;
import com.clinvest.migraine.server.data.UserRole;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StudySimulation
{
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  public static final long SECOND = 1000;
  public static final long MINUTE = 60 * SECOND;
  public static final long HOUR = 60 * MINUTE;
  public static final long DAY = 24 * HOUR;
  
  protected Gson gson;
  protected User[] participants;
  Date studyStart;
  Date studyEnd;
  Random random;
  
  public StudySimulation() throws ParseException
  {
    gson = new GsonBuilder().setLenient().create();
    participants = gson.fromJson(simulationUsers, User[].class);
    studyStart = dateFormat.parse("2020-03-15");
    studyEnd = new Date(studyStart.getTime() + 60 * DAY);
    random = new Random(System.currentTimeMillis());
  }
  
  public void createSimulationUsers()
  {
    Study s = Study.getByName("Development and Test Study").get(0);
    Role r = Role.getByName("user");
    Timestamp begin = new Timestamp(studyStart.getTime());
    
    for (User u : participants)
    {
      u.setId(UUID.randomUUID());
      u.setConfirmed(begin);
      u.setStudy(s);
      u.setPassword(UnixCrypt.crypt(u.getPassword(), "MA"));

      User.save(u);
      
      UserRole ur = new UserRole();
      ur.setUser(u);
      ur.setRole(r);
      
      UserRole.save(ur);
      
      UserConsent uc = new UserConsent();
      uc.setUser(u);
      uc.setConsentDate(begin);
      uc.setConsent(true);
      
      UserConsent.save(uc);
    }
  }
  
  public void runSimulation()
  {
    Date simTime = new Date(studyStart.getTime());
    Map<UUID, DiaryEntry> lastEntryMap = new HashMap<>();
    List<Medication> meds = Medication.listAcute();
    
    // Do a random FAMS entry for each user at the beginning (in the first 10 days)
    System.out.println("Initial FAMS entries.");
    for (User u : participants)
    {
      Timestamp famsTime = new Timestamp(simTime.getTime() + random.nextInt(10) * DAY);
      simulateFamsEntry(u, famsTime);
    }
    
    // Simulate each day in the simulation period.
    while (simTime.before(studyEnd))
    {
      System.out.println("Sim time: " + simTime.toString());
      // Do a random Diary entry for each user each day (with a small chance of skipping a day)
      for (User u : participants)
      {
        System.out.println("Participant: " + u.getLastName());
        // Users will make an entry 98% of the time. 
        double pct = random.nextDouble();
        if (pct <= 0.98)
        {
          DiaryEntry d = new DiaryEntry();
          d.setUser(u);
          d.setStudy(u.getStudy());
          d.setEntryTimestamp(new Timestamp(simTime.getTime()));
          
          pct = random.nextDouble();
          // 1 in 5 chance of a migraine on any given day
          if (pct < 0.20)
          {
            int severity = random.nextInt(3) + 1;
            d.setSeverity(SEVERITY[severity]);
            
            if (lastEntryMap.containsKey(u.getId()))
            {
              DiaryEntry lastEntry = lastEntryMap.get(u.getId());
              if (!lastEntry.getSeverity().equals(SEVERITY[0]))
              {
                d.setNewHeadache(false);
                d.setHours(lastEntry.getHours() + 24);
              }
              else
              {
                d.setNewHeadache(true);
                d.setHours(random.nextDouble() * (random.nextInt(12) + 1));
              }
            }
            else
            {
              d.setNewHeadache(true);
              d.setHours(random.nextDouble() * (random.nextInt(8) + 1));
            }
            d.setPainDirectional(random.nextBoolean());
            d.setPainThrobbing(random.nextBoolean());
            d.setPainWorse(random.nextBoolean());
            d.setNausea(random.nextBoolean());
            d.setLightSoundSensitive(random.nextBoolean());
            d.setWorstSymptom(worstSymptom(d));
            d.setTookMedication(random.nextBoolean());
          }
          else
          {
            d.setSeverity(SEVERITY[0]);
          }
          DiaryEntry.save(d);
          lastEntryMap.put(u.getId(), d);
          
          if (d.getTookMedication())
          {
            int medsTaken = random.nextInt(2) + 1;
            int lastMed = -1;
            for (int i = 0; i < medsTaken; i++)
            {
              DiaryMedicationEntry dm = new DiaryMedicationEntry();
              dm.setEntry(d);
              int randMed = random.nextInt(meds.size());
              while (randMed == lastMed)
              {
                randMed = random.nextInt(meds.size());
              }
              Medication m = meds.get(randMed);
              lastMed = randMed;
              
              dm.setMedication(m);
              dm.setPainDecrease(random.nextBoolean());
              dm.setHowOften(MED_FREQ[random.nextInt(MED_FREQ.length)]);
              
              DiaryMedicationEntry.save(dm);
              
            }
          }
        }
      }
      // Add a day
      simTime = new Date(simTime.getTime() + DAY);
    }
    
    // Do a random FAMS entry for each user at the end (in the last 10 days)
    for (User u : participants)
    {
      Timestamp famsTime = new Timestamp(simTime.getTime() - (random.nextInt(10) + 1) * DAY);
      simulateFamsEntry(u, famsTime);
    }
  }
  
  private static final String[] SEVERITY = {"No pain", "Mild pain", "Moderate pain", "Severe pain"};
  private static final String[] MED_FREQ = {"Once", "Every 2 hours", "Every 4 hours", "Every 6 hours"};
      

  
  private String worstSymptom(DiaryEntry d)
  {
    List<String> symptoms = new ArrayList<String>();
    symptoms.add("Pain");
    symptoms.add("Dim vision");
    symptoms.add("Irritability");
    symptoms.add("Teeth itching");
    symptoms.add("Insomnia");
    if (d.getLightSoundSensitive())
    {
      symptoms.add("Light sensitivity");
      symptoms.add("Sound sensitivity");
    }
    if (d.getNausea())
    {
      symptoms.add("Nausea");
      symptoms.add("Vomiting");
    }
    if (d.getPainThrobbing())
    {
      symptoms.add("Throbbing");
    }
    return symptoms.get(random.nextInt(symptoms.size()));
  }
  
  protected void simulateFamsEntry(User u, Timestamp created)
  {
    FamsEntry fe = new FamsEntry();
    fe.setUser(u);
    fe.setStudy(u.getStudy());
    fe.setQ1(random.nextInt(6));
    fe.setQ2(random.nextInt(6));
    fe.setQ3(random.nextInt(6));
    fe.setQ4(random.nextInt(6));
    fe.setQ5(random.nextInt(6));
    fe.setQ6(random.nextInt(6));
    fe.setQ7(random.nextInt(6));
    fe.setQ8(random.nextInt(6));
    fe.setQ9(random.nextInt(6));
    fe.setQ10(random.nextInt(6));
    fe.setQ11(random.nextInt(6));
    fe.setQ12(random.nextInt(6));
    fe.setQ13(random.nextInt(6));
    fe.setQ14(random.nextInt(6));
    fe.setQ15(random.nextInt(6));
    fe.setQ16(random.nextInt(6));
    fe.setQ17(random.nextInt(6));
    fe.setQ18(random.nextInt(6));
    fe.setQ19(random.nextInt(6));
    fe.setQ20(random.nextInt(6));
    fe.setQ21(random.nextInt(6));
    fe.setQ22(random.nextInt(6));
    fe.setQ23(random.nextInt(6));
    fe.setQ24(random.nextInt(6));
    fe.setQ25(random.nextInt(6));
    fe.setQ26(random.nextInt(6));
    fe.setQ27(random.nextInt(6));
    
    FamsEntry.save(fe);
  }
  
  public static void main(String[] args)
  {
    Database.initialize();
    
    try
    {
      StudySimulation sim = new StudySimulation(); 
      sim.createSimulationUsers();
      sim.runSimulation();
    }
    catch (ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  
  private static final String simulationUsers = "[" +
      "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"sex\":\"Male\", \"birthDate\": \"Apr 1, 2000 12:00:00 AM\", \"email\":\"jdoe@example.com\", \"password\": \"password\", \"start\": \"Sep 12, 2014 12:00:00 AM\", \"diagnosed\": false }," +
      "{ \"firstName\": \"Jane\", \"lastName\": \"Johnson\", \"sex\":\"Female\", \"birthDate\": \"May 2, 2001 12:00:00 AM\", \"email\":\"jjohnson@example.com\", \"password\": \"password\", \"diagnosed\": true, \"start\": \"Feb 11, 2016 12:00:00 AM\", \"diagnosis\": \"Chronic Migraine\", \"diagnosisDate\": \"Jun 4, 2017 12:00:00 AM\" },\n" +
      "{ \"firstName\": \"Ralph\", \"lastName\": \"Kramden\", \"sex\":\"Male\", \"birthDate\": \"Oct 12, 1958 12:00:00 AM\", \"email\":\"rkramden@example.com\", \"password\": \"password\", \"diagnosed\": true, \"start\": \"Jul 17, 1976 12:00:00 AM\", \"diagnosis\": \"Chronic Migraine\", \"diagnosisDate\": \"Nov 4, 1978 12:00:00 AM\" },\n" +
      "{ \"firstName\": \"Ed\", \"lastName\": \"Norton\", \"sex\":\"Male\", \"birthDate\": \"Feb 21, 1960 12:00:00 AM\", \"email\":\"enorton@example.com\", \"password\": \"password\", \"diagnosed\": true, \"start\": \"Sep 8, 1990 12:00:00 AM\", \"diagnosis\": \"Chronic Migraine\", \"diagnosisDate\": \"Aug 5, 1999 12:00:00 AM\" },\n" +
      "{ \"firstName\": \"Lucille\", \"lastName\": \"Ball\", \"sex\":\"Female\", \"birthDate\": \"Sep 20, 1965 12:00:00 AM\", \"email\":\"lball@example.com\", \"password\": \"password\", \"diagnosed\": true, \"start\": \"Aug 18, 2014 12:00:00 AM\", \"diagnosis\": \"Chronic Migraine\", \"diagnosisDate\": \"Jun 24, 2000 12:00:00 AM\" },\n" +
      "{ \"firstName\": \"Donna\", \"lastName\": \"Davis\", \"sex\":\"Female\", \"birthDate\": \"Apr 7, 2000 12:00:00 AM\", \"email\":\"ddavis@example.com\", \"password\": \"password\", \"diagnosed\": false, \"start\": \"Sep 29, 2019 12:00:00 AM\" },\n" +
      "{ \"firstName\": \"Laura\", \"lastName\": \"Ingalls\", \"sex\":\"Female\", \"birthDate\": \"Mar 18, 1997 12:00:00 AM\", \"email\":\"lingalls@example.com\", \"password\": \"password\", \"start\": \"May 12, 2008 12:00:00 AM\", \"diagnosed\": true, \"diagnosis\": \"Chronic Migraine\", \"diagnosisDate\": \"Jan 14, 2010 12:00:00 AM\" },\n" +
      "{ \"firstName\": \"Michelle\", \"lastName\": \"Knight\", \"sex\":\"Female\", \"birthDate\": \"Nov 22, 1968 12:00:00 AM\", \"email\":\"mknight@example.com\", \"password\": \"password\", \"start\": \"Oct 12, 1982 12:00:00 AM\", \"diagnosed\": true, \"diagnosis\": \"Chronic Migraine\", \"diagnosisDate\": \"Dec 14, 1988 12:00:00 AM\" },\n" +
      "{ \"firstName\": \"Julia\", \"lastName\": \"Sanchez\", \"sex\":\"Female\", \"birthDate\": \"July 23, 1965 12:00:00 AM\", \"email\":\"jsanchez@example.com\", \"password\": \"password\", \"start\": \"Sep 12, 2014 12:00:00 AM\", \"diagnosed\": true, \"diagnosis\": \"Chronic Migraine\", \"diagnosisDate\": \"Aug 15, 2002 12:00:00 AM\" },\n" +
      "{ \"firstName\": \"Linda\", \"lastName\": \"Lee\", \"sex\":\"Female\", \"birthDate\": \"Oct 20, 1999 12:00:00 AM\", \"email\":\"llee@example.com\", \"password\": \"password\", \"start\": \"Nov 14, 2018 12:00:00 AM\", \"diagnosed\": true, \"diagnosis\": \"Chronic Migraine\", \"diagnosisDate\": \"Jan 4, 2019 12:00:00 AM\" }\n" +
      "]";
  
}
