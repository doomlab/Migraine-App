package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_roles")
public class UserRole
{
  protected Long id;
  protected UUID userId;
  protected Long roleId;
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

  @Type(type = "uuid-char")
  @Column(name = "user_id")
  public UUID getUserId()
  {
    return userId;
  }

  public void setUserId(UUID userId)
  {
    this.userId = userId;
  }

  public Long getRoleId()
  {
    return roleId;
  }

  @Column(name="role_id")
  public void setRoleId(Long roleId)
  {
    this.roleId = roleId;
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

  public static void save(UserRole role)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(role);

      session.getTransaction().commit();
    }
  }
  
  @SuppressWarnings("unchecked")
  public static List<UserRole> list()
  {

    List<UserRole> roles;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      roles = session.createQuery("from UserRole").list();
    }
    return roles;
  }
}
