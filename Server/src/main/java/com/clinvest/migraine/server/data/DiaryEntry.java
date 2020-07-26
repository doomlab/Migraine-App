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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "entry_timestamp")
  protected Timestamp entryTimestamp;
  @Column(name = "severity")
  protected String    severity;
  @Column(name = "new_headache")
  protected Boolean   newHeadache;
  @Column(name = "hours")
  protected Double    hours;
  @Column(name = "pain_directional")
  protected Boolean   painDirectional;
  @Column(name = "pain_throbbing")
  protected Boolean   painThrobbing;
  @Column(name = "pain_worse")
  protected Boolean   painWorse;
  @Column(name = "nausea")
  protected Boolean   nausea;
  @Column(name = "light_sound_sensitive")
  protected Boolean   lightSoundSensitive;
  @Column(name = "worst_symptom")
  protected String    worstSymptom;
  @Column(name = "took_medication")
  protected Boolean   tookMedication;
  
  @OneToMany(mappedBy="id")
  private Set<DiaryMedicationEntry> medications;
  
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

  public Boolean getNewHeadache()
  {
    return newHeadache;
  }

  public void setNewHeadache(Boolean newHeadache)
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

  public Boolean getPainDirectional()
  {
    return painDirectional;
  }

  public void setPainDirectional(Boolean painDirectional)
  {
    this.painDirectional = painDirectional;
  }

  public Boolean getPainThrobbing()
  {
    return painThrobbing;
  }

  public void setPainThrobbing(Boolean painThrobbing)
  {
    this.painThrobbing = painThrobbing;
  }

  public Boolean getPainWorse()
  {
    return painWorse;
  }

  public void setPainWorse(Boolean painWorse)
  {
    this.painWorse = painWorse;
  }

  public Boolean getNausea()
  {
    return nausea;
  }

  public void setNausea(Boolean nausea)
  {
    this.nausea = nausea;
  }

  public Boolean getLightSoundSensitive()
  {
    return lightSoundSensitive;
  }

  public void setLightSoundSensitive(Boolean lightSoundSensitive)
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

  public Boolean getTookMedication()
  {
    return tookMedication;
  }

  public void setTookMedication(Boolean tookMedication)
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
