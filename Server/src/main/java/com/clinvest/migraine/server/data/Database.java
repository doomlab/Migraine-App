package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Database
{
  private static Database instance;

  private static final String[][] USERS = { { "ef8ed6b2-9d4f-43c3-b253-ef4d2eb544f5", "Root", "User",
      "root@insufficient-light.com", "Root was always born yesterday", "THMbMigabq1Bs" }, };

  public static void initialize()
  {
    if (null == instance)
    {
      instance = new Database();
    }
  }

  private Database()
  {
    // Check to see if the root user exists. If not, create all of the demo users.
    User root = User.getById(UUID.fromString(USERS[0][0]));
    if (null == root)
    {
      createDemoUsers();
    }
  }

  private void createDemoUsers()
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
      u.setPassword(data[5]);
      u.setCreated(Timestamp.valueOf(LocalDateTime.now()));

      User.save(u);
    }
  }

  // Test the database creation
  public static void main(String[] args)
  {
    Database.initialize();
  }
}
