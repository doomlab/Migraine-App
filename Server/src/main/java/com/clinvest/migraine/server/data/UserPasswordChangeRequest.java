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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.hibernate.query.Query;

@Entity
@Table(name = "user_pwd_change")
public class UserPasswordChangeRequest
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  protected Long id;
  
  @ManyToOne
  @Type(type = "uuid-char")
  @JoinColumn(name="user_id", nullable=false)
  protected User user;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="requested")
  protected Timestamp requested;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="changed")
  protected Timestamp changed;
  
  @Column(name="source")
  protected String source;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created", updatable = false, nullable = false)
  protected Timestamp created;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "last_modified", updatable = false, nullable = false)
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

  public Timestamp getRequested()
  {
    return requested;
  }

  public void setRequested(Timestamp requested)
  {
    this.requested = requested;
  }

  public Timestamp getChanged()
  {
    return changed;
  }

  public void setChanged(Timestamp changed)
  {
    this.changed = changed;
  }

  public String getSource()
  {
    return source;
  }

  public void setSource(String source)
  {
    this.source = source;
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

  public static UserPasswordChangeRequest getById(Long id)
  {

    UserPasswordChangeRequest userPasswordChangeRequest;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      userPasswordChangeRequest = session.get(UserPasswordChangeRequest.class, id);
    }

    return userPasswordChangeRequest;
  }
  
  @SuppressWarnings("unchecked")
  public static List<UserPasswordChangeRequest> getByUserId(UUID userId)
  {

    List<UserPasswordChangeRequest> userPasswordChangeRequest = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      Query<UserPasswordChangeRequest> query = session.createQuery("from UserPasswordChangeRequest where user_id = :userId ");
      query.setParameter("userId", userId);

      userPasswordChangeRequest = query.list();
      
    }

    return userPasswordChangeRequest;
  }
  
  public static void save(UserPasswordChangeRequest userPasswordChangeRequest)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(userPasswordChangeRequest);

      session.getTransaction().commit();
    }
  }
}
