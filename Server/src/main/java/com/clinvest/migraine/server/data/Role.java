package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.query.Query;

@Entity
@Table(name = "roles")
public class Role
{
  protected Long id;
  protected String name;
  protected String description;
  protected Timestamp created;
  
  @Id
  @GeneratedValue
  @Column(name="id")
  public Long getId()
  {
    return id;
  }
  public void setId(Long id)
  {
    this.id = id;
  }
  @Column(name="name")
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  @Column(name="description")
  public String getDescription()
  {
    return description;
  }
  public void setDescription(String description)
  {
    this.description = description;
  }
  @Column(name="created")
  public Timestamp getCreated()
  {
    return created;
  }
  public void setCreated(Timestamp created)
  {
    this.created = created;
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
