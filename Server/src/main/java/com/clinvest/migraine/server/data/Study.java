package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.query.Query;

@Entity
@Table(name = "studies")
public class Study
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  protected Long id;
  @Column(name="name")
  protected String name;
  @Column(name="description")
  protected String description;
  @Column(name="start_date")
  protected Timestamp startDate;
  @Column(name="end_date")
  protected Timestamp endDate;
  @Column(name="sponsor")
  protected String sponsor;
  @Column(name="investigator")
  protected String investigator;
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
  
  public String getSponsor()
  {
    return sponsor;
  }
  public void setSponsor(String sponsor)
  {
    this.sponsor = sponsor;
  }
  public String getInvestigator()
  {
    return investigator;
  }
  public void setInvestigator(String investigator)
  {
    this.investigator = investigator;
  }
  public Long getId()
  {
    return id;
  }
  public void setId(Long id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getDescription()
  {
    return description;
  }
  public void setDescription(String description)
  {
    this.description = description;
  }
  public Timestamp getStartDate()
  {
    return startDate;
  }
  public void setStartDate(Timestamp startDate)
  {
    this.startDate = startDate;
  }
  public Timestamp getEndDate()
  {
    return endDate;
  }
  public void setEndDate(Timestamp endDate)
  {
    this.endDate = endDate;
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

  public static Study getById(Long id)
  {

    Study study;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      study = session.get(Study.class, id);
    }

    return study;
  }
  
  @SuppressWarnings("unchecked")
  public static List<Study> getByName(String name)
  {

    List<Study> study = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      Query<Study> query = session.createQuery("from Study where name = :name ");
      query.setParameter("name", name);

      study = query.list();
      
    }

    return study;
  }
  
  public static void save(Study study)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(study);

      session.getTransaction().commit();
    }
  }
  
}
