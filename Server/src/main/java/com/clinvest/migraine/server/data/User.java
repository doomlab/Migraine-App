package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.hibernate.query.Query;

@Entity
@Table(name = "users")
public class User
{

  protected UUID      id;
  protected String    firstName;
  protected String    lastName;
  protected Timestamp birthDate;
  protected String    email;
  protected String    password;
  protected Timestamp confirmed;
  protected Timestamp created;

  @Id
  @Type(type = "uuid-char")
  @Column(name = "id")
  public UUID getId()
  {
    return id;
  }

  public void setId(UUID id)
  {
    this.id = id;
  }

  @Column(name = "first_name")
  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  @Column(name = "last_name")
  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  @Column(name = "birth_date")
  public Timestamp getBirthDate()
  {
    return birthDate;
  }

  public void setBirthDate(Timestamp birthDate)
  {
    this.birthDate = birthDate;
  }

  @Column(name = "email")
  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  @Column(name = "password")
  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  @Column(name = "confirmed")
  public Timestamp getConfirmed()
  {
    return confirmed;
  }

  public void setConfirmed(Timestamp confirmed)
  {
    this.confirmed = confirmed;
  }

  @Column(name = "created")
  public Timestamp getCreated()
  {
    return created;
  }

  public void setCreated(Timestamp created)
  {
    this.created = created;
  }

  public static User getById(UUID id)
  {

    User user;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      user = session.get(User.class, id);
    }

    return user;
  }

  @SuppressWarnings("unchecked")
  public static User getByEmail(String email)
  {

    User user = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      Query<User> query = session.createQuery("from User where email = :email ");
      query.setParameter("email", email);

      List<User> list = query.list();
      if (null != list && list.size() > 0)
      {
        user = list.get(0);
      }
    }

    return user;
  }

  @SuppressWarnings("unchecked")
  public static List<User> list()
  {

    List<User> users;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      users = session.createQuery("from User").list();
    }
    return users;
  }

  public static void save(User user)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(user);

      session.getTransaction().commit();
    }
  }
  
  public static void update(User user) {
    
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        session.beginTransaction();
        
        session.update(user);
        
        session.getTransaction().commit();
    }
  }

}
