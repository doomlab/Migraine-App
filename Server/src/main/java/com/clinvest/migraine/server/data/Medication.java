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

import org.hibernate.Session;

@Entity
@Table(name = "medication")
public class Medication
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  protected Long      id;

  @Column(name="category")
  protected String category;
  
  @Column(name = "name")
  protected String    name;

  @Column(name = "formulary")
  protected String    formulary;

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
  
  public String getCategory()
  {
    return category;
  }

  public void setCategory(String category)
  {
    this.category = category;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getFormulary()
  {
    return formulary;
  }

  public void setFormulary(String formulary)
  {
    this.formulary = formulary;
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

  @SuppressWarnings("unchecked")
  public static List<Medication> list()
  {

    List<Medication> meds = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      meds = session.createQuery("from Medication").list();
    }
    return meds;
  }
  
  @SuppressWarnings("unchecked")
  public static List<Medication> listAcute()
  {

    List<Medication> meds = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      meds = session.createQuery("from Medication where category = 'acute'").list();
    }
    return meds;
  }
  
  @SuppressWarnings("unchecked")
  public static List<Medication> listPreventative()
  {

    List<Medication> meds = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      meds = session.createQuery("from Medication where category = 'preventative'").list();
    }
    return meds;
  }
  
  public static void save(Medication medication)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(medication);

      session.getTransaction().commit();
    }
  }
}
