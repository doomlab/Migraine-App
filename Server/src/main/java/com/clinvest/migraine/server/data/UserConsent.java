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
@Table(name = "user_consent")
public class UserConsent
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  protected Long id;
  
  @ManyToOne
  @Type(type = "uuid-char")
  @JoinColumn(name="user_id", nullable=false)
  protected User user;
  
  @Column(name="consent", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean consent;
  
  @Column(name="consentDate")
  protected Timestamp consentDate;
  

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

  public boolean getConsent()
  {
    return consent;
  }

  public void setConsent(boolean consent)
  {
    this.consent = consent;
  }

  public Timestamp getConsentDate()
  {
    return consentDate;
  }

  public void setConsentDate(Timestamp consentDate)
  {
    this.consentDate = consentDate;
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

  public static UserConsent getById(Long id)
  {

    UserConsent userConsent;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      userConsent = session.get(UserConsent.class, id);
    }

    return userConsent;
  }
  
  @SuppressWarnings("unchecked")
  public static List<UserConsent> getByUser(User user)
  {

    List<UserConsent> userConsent = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      Query<UserConsent> query = session.createQuery("from UserConsent u where u.user = :user order by u.consentDate desc");
      query.setParameter("user", user);

      userConsent = query.list();
      
    }

    return userConsent;
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
