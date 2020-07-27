package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.hibernate.query.Query;



@Entity
@Table(name = "users")
public class User
{
  @Id
  @Type(type = "uuid-char")
  @Column(name = "id", updatable = false, nullable = false)
  protected UUID      id;
  @Column(name = "first_name")
  protected String    firstName;
  @Column(name = "last_name")
  protected String    lastName;
  @Column(name="sex")
  protected String sex;
  @Column(name = "birth_date")
  protected Timestamp birthDate;
  @Column(name = "email")
  protected String    email;
  @Column(name = "password")
  protected String    password;

  @Column(name="start_date")
  protected Timestamp   start;
  @Column(name="diagnosed", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean   diagnosed;
  @Column(name="diagnosis")
  protected String diagnosis;

  @Column(name="diagnosis_date")
  protected Timestamp diagnosisDate;

  @Column(name = "confirmed")
  protected Timestamp confirmed;
  
  @OneToMany(mappedBy="id")
  private Set<UserConsent> consent;
  
  @OneToMany(mappedBy="id")
  private Set<UserRole> roles;
  
  @OneToMany(mappedBy="sessionId")
  private Set<UserSession> sessions;
  
  @OneToMany(mappedBy="id")
  private Set<UserLockout> lockout;
  
  @OneToMany(mappedBy="id")
  private Set<UserPasswordChangeRequest> changeRequests;
  
  @ManyToOne
  private Study study;
  
  @OneToMany(mappedBy="id")
  private Set<UserStudyPayment> payments;
  
  @OneToMany(mappedBy="id")
  private Set<DiaryEntry> diaryEntries;
  
  @OneToMany(mappedBy="id")
  private Set<FamsEntry> famsEntries;
  

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

  public UUID getId()
  {
    return id;
  }

  public void setId(UUID id)
  {
    this.id = id;
  }


  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }


  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }


  public String getSex()
  {
    return sex;
  }

  public void setSex(String sex)
  {
    this.sex = sex;
  }

  public Set<UserPasswordChangeRequest> getChangeRequests()
  {
    return changeRequests;
  }

  public void setChangeRequests(Set<UserPasswordChangeRequest> changeRequests)
  {
    this.changeRequests = changeRequests;
  }

  public Timestamp getBirthDate()
  {
    return birthDate;
  }

  public void setBirthDate(Timestamp birthDate)
  {
    this.birthDate = birthDate;
  }


  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public Timestamp getStart()
  {
    return start;
  }

  public void setStart(Timestamp start)
  {
    this.start = start;
  }

  public boolean getDiagnosed()
  {
    return diagnosed;
  }

  public void setDiagnosed(boolean diagnosed)
  {
    this.diagnosed = diagnosed;
  }

  public String getDiagnosis()
  {
    return diagnosis;
  }

  public void setDiagnosis(String diagnosis)
  {
    this.diagnosis = diagnosis;
  }

  public Timestamp getDiagnosisDate()
  {
    return diagnosisDate;
  }

  public void setDiagnosisDate(Timestamp diagnosisDate)
  {
    this.diagnosisDate = diagnosisDate;
  }

  public Timestamp getConfirmed()
  {
    return confirmed;
  }

  public void setConfirmed(Timestamp confirmed)
  {
    this.confirmed = confirmed;
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

  public Set<UserConsent> getConsent()
  {
    return consent;
  }

  public void setConsent(Set<UserConsent> consent)
  {
    this.consent = consent;
  }

  public Set<UserRole> getRoles()
  {
    return roles;
  }

  public void setRoles(Set<UserRole> roles)
  {
    this.roles = roles;
  }

  public Set<UserSession> getSessions()
  {
    return sessions;
  }

  public void setSessions(Set<UserSession> sessions)
  {
    this.sessions = sessions;
  }

  public Set<UserLockout> getLockout()
  {
    return lockout;
  }

  public void setLockout(Set<UserLockout> lockout)
  {
    this.lockout = lockout;
  }

  public Study getStudy()
  {
    return study;
  }

  public void setStudy(Study study)
  {
    this.study = study;
  }

  public Set<UserStudyPayment> getPayments()
  {
    return payments;
  }

  public void setPayments(Set<UserStudyPayment> payments)
  {
    this.payments = payments;
  }

  public Set<DiaryEntry> getDiaryEntries()
  {
    return diaryEntries;
  }

  public void setDiaryEntries(Set<DiaryEntry> diaryEntries)
  {
    this.diaryEntries = diaryEntries;
  }

  public Set<FamsEntry> getFamsEntries()
  {
    return famsEntries;
  }

  public void setFamsEntries(Set<FamsEntry> famsEntries)
  {
    this.famsEntries = famsEntries;
  }

  public static User getById(UUID id)
  {

    User user;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      user = session.get(User.class, id);
    }

    return user;
  }

  @SuppressWarnings("unchecked")
  public static User getByEmail(String email)
  {

    User user = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      Query<User> query = session.createQuery("from User where email = :email ");
      query.setParameter("email", email);

      List<User> list = query.list();
      if (null != list && list.size() > 0)
      {
        user = list.get(0);
      }
    }

    return user;
  }

  @SuppressWarnings("unchecked")
  public static List<User> list()
  {

    List<User> users;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      users = session.createQuery("from User").list();
    }
    return users;
  }

  public static void save(User user)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(user);

      session.getTransaction().commit();
    }
  }
  
  public static void update(User user) {
    
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        session.beginTransaction();
        
        session.update(user);
        
        session.getTransaction().commit();
    }
  }

}
