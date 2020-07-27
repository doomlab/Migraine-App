package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "diary")
public class DiaryEntry
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long        id;
  @ManyToOne
  @Type(type = "uuid-char")
  @JoinColumn(name = "user_id", nullable = false)
  protected User      user;
  @ManyToOne
  @JoinColumn(name = "study_id", nullable = false)
  protected Study     study;

  @Column(name = "entry_timestamp")
  protected Timestamp entryTimestamp;
  @Column(name = "severity")
  protected String    severity;
  @Column(name = "new_headache", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean   newHeadache;
  @Column(name = "hours")
  protected Double    hours;
  @Column(name = "pain_directional", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean   painDirectional;
  @Column(name = "pain_throbbing", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean   painThrobbing;
  @Column(name = "pain_worse", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean   painWorse;
  @Column(name = "nausea", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean   nausea;
  @Column(name = "light_sound_sensitive", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean   lightSoundSensitive;
  @Column(name = "worst_symptom")
  protected String    worstSymptom;
  @Column(name = "took_medication", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean   tookMedication;
  
  @OneToMany(mappedBy="id")
  private Set<DiaryMedicationEntry> medications;
  
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

  public Study getStudy()
  {
    return study;
  }

  public void setStudy(Study study)
  {
    this.study = study;
  }

  public Timestamp getEntryTimestamp()
  {
    return entryTimestamp;
  }

  public void setEntryTimestamp(Timestamp entryTimestamp)
  {
    this.entryTimestamp = entryTimestamp;
  }

  public Timestamp getCreated()
  {
    return created;
  }

  public void setCreated(Timestamp created)
  {
    this.created = created;
  }

  public String getSeverity()
  {
    return severity;
  }

  public void setSeverity(String severity)
  {
    this.severity = severity;
  }

  public boolean getNewHeadache()
  {
    return newHeadache;
  }

  public void setNewHeadache(boolean newHeadache)
  {
    this.newHeadache = newHeadache;
  }

  public Double getHours()
  {
    return hours;
  }

  public void setHours(Double hours)
  {
    this.hours = hours;
  }

  public boolean getPainDirectional()
  {
    return painDirectional;
  }

  public void setPainDirectional(boolean painDirectional)
  {
    this.painDirectional = painDirectional;
  }

  public boolean getPainThrobbing()
  {
    return painThrobbing;
  }

  public void setPainThrobbing(boolean painThrobbing)
  {
    this.painThrobbing = painThrobbing;
  }

  public boolean getPainWorse()
  {
    return painWorse;
  }

  public void setPainWorse(boolean painWorse)
  {
    this.painWorse = painWorse;
  }

  public boolean getNausea()
  {
    return nausea;
  }

  public void setNausea(boolean nausea)
  {
    this.nausea = nausea;
  }

  public boolean getLightSoundSensitive()
  {
    return lightSoundSensitive;
  }

  public void setLightSoundSensitive(boolean lightSoundSensitive)
  {
    this.lightSoundSensitive = lightSoundSensitive;
  }

  public String getWorstSymptom()
  {
    return worstSymptom;
  }

  public void setWorstSymptom(String worstSymptom)
  {
    this.worstSymptom = worstSymptom;
  }

  public boolean getTookMedication()
  {
    return tookMedication;
  }

  public void setTookMedication(boolean tookMedication)
  {
    this.tookMedication = tookMedication;
  }

  public static void save(DiaryEntry diary)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(diary);

      session.getTransaction().commit();
    }
  }

}
