package com.clinvest.migraine.server.data;

import java.io.Serializable;
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
@Table(name="sessions")
public class UserSession implements Serializable
{
  public static final long DEFAULT_SESSION_LENGTH = 60 * 60 * 1000; // one hour in milliseconds
  
  private static final long serialVersionUID = 1L;
  
  protected UUID sessionId;
  protected UUID userId;
  protected Timestamp active;
  
  @Id
  @Column(name = "id")
  @Type(type = "uuid-char")
  public UUID getSessionId()
  {
    return sessionId;
  }
  public void setSessionId(UUID sessionId)
  {
    this.sessionId = sessionId;
  }
  
  @Column(name = "user_id")
  @Type(type = "uuid-char")
  public UUID getUserId()
  {
    return userId;
  }
  public void setUserId(UUID userId)
  {
    this.userId = userId;
  }
  
  @Column(name = "active")
  public Timestamp getActive()
  {
    return active;
  }
  public void setActive(Timestamp active)
  {
    this.active = active;
  }
  
  @Override
  public String toString() {
      
      return String.format("Session Id: %s, User Id: %s, Active: %d", 
              sessionId.toString(), userId.toString(), active.getTime());
  }  

  public static UserSession getById(UUID id) {
    
    UserSession us;
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        us = session.get(UserSession.class, id);
    }
    
    return us;
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static UserSession getByUser(UUID user) {
    
    UserSession us = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
      Query query = session.createQuery("from UserSession where user_id = :user ");
      query.setParameter("user", user);
      List<UserSession> list = query.list();
      if (null != list)
      {
        us = list.get(0);
      }
    }
    
    return us;
  }
  
  @SuppressWarnings("unchecked")
  public static List<UserSession> list() {
    
    List<UserSession> us;
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        us = session.createQuery("from UserSession").list();
    }
    return us;
  }
  
  public static void save(UserSession us) {
    
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        session.beginTransaction();
        
        session.save(us);
        
        session.getTransaction().commit();
    }
  }
  
public static void delete(UserSession us) {
    
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        session.beginTransaction();
        
        session.delete(us);
        
        session.getTransaction().commit();
    }
  }
}
