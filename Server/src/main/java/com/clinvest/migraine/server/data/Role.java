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
@Table(name = "roles")
public class Role
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  protected Long id;
  @Column(name="name")
  protected String name;
  @Column(name="description")
  protected String description;
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

  public static void save(Role role)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(role);

      session.getTransaction().commit();
    }
  }
  
  @SuppressWarnings("unchecked")
  public static List<Role> list()
  {

    List<Role> roles;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      roles = session.createQuery("from Role").list();
    }
    return roles;
  }
  
  @SuppressWarnings("unchecked")
  public static Role getByName(String name)
  {

    Role role = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      Query<Role> query = session.createQuery("from Role where name = :name ");
      query.setParameter("name", name);
      List<Role> list = query.list();
      if (null != list && list.size() > 0)
      {
        role = list.get(0);
      }
    }

    return role;
  }

}
