package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "user_lockout")
public class UserLockout
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  protected Long id;
  
  @ManyToOne
  @Type(type = "uuid-char")
  @JoinColumn(name="user_id", nullable=false)
  protected User user;
  
  @Column(name="locked")
  protected Timestamp locked;
  
  @Column(name="cleared")
  protected Timestamp cleared;
  
  @Column(name="reason")
  protected String reason;

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

  
  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Timestamp getLocked()
  {
    return locked;
  }

  public void setLocked(Timestamp locked)
  {
    this.locked = locked;
  }

  public Timestamp getCleared()
  {
    return cleared;
  }

  public void setCleared(Timestamp cleared)
  {
    this.cleared = cleared;
  }

  public String getReason()
  {
    return reason;
  }

  public void setReason(String reason)
  {
    this.reason = reason;
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

  public static UserLockout getById(Long id)
  {

    UserLockout userLockout;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      userLockout = session.get(UserLockout.class, id);
    }

    return userLockout;
  }
  
  @SuppressWarnings("unchecked")
  public static List<UserLockout> getByUserId(UUID userId)
  {

    List<UserLockout> userLockout = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      Query<UserLockout> query = session.createQuery("from UserLockout where user_id = :userId ");
      query.setParameter("userId", userId);

      userLockout = query.list();
      
    }

    return userLockout;
  }
  
  public static void save(UserConsent userConsent)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(userConsent);

      session.getTransaction().commit();
    }
  }
}
