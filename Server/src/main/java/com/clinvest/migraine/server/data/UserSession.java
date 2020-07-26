package com.clinvest.migraine.server.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
  
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @Type(type = "uuid-char")
  protected UUID sessionId;
  @ManyToOne
  @Type(type = "uuid-char")
  @JoinColumn(name="user_id", nullable=false)
  protected User user;
  @Column(name = "active")
  protected Timestamp active;
  
  @Column(name = "created", updatable = false, nullable = false)
  protected Timestamp created;
  @Column(name = "last_modified")
  protected Timestamp modified;

  @PrePersist
  protected void onCreate()
  {
    if (created == null)
    {
      created = Timestamp.valueOf(LocalDateTime.now());
    }
  }
  
  @PreUpdate
  protected void onUpdate()
  {
    modified = Timestamp.valueOf(LocalDateTime.now());
  }

  
  
  public UUID getSessionId()
  {
    return sessionId;
  }
  public void setSessionId(UUID sessionId)
  {
    this.sessionId = sessionId;
  }
  
  
  public User getUser()
  {
    return user;
  }
  public void setUser(User user)
  {
    this.user = user;
  }
  
 
  public Timestamp getActive()
  {
    return active;
  }
  public void setActive(Timestamp active)
  {
    this.active = active;
  }
  
  public Timestamp getCreated()
  {
    return created;
  }

  public void setCreated(Timestamp created)
  {
    this.created = created;
  }

  public Timestamp getModified()
  {
    return modified;
  }

  public void setModified(Timestamp modified)
  {
    this.modified = modified;
  }

  @Override
  public String toString() {
      
      return String.format("Session Id: %s, User Id: %s, Active: %d", 
              sessionId.toString(), user.getId().toString(), active.getTime());
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
      if (null != list && list.size() > 0)
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
