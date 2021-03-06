package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

@Entity
@Table(name = "diary_meds")
public class DiaryMedicationEntry
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  protected Long id;
  @ManyToOne
  @JoinColumn(name="entry_id", nullable=false)
  protected DiaryEntry entry;
  @ManyToOne
  @JoinColumn(name="medication_id")
  protected Medication medication;
  @Column(name="how_often")
  protected String howOften;
  @Column(name="pain_decrease", nullable = false, columnDefinition = "TINYINT", length = 1)
  protected boolean painDecrease;
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

  public DiaryEntry getEntry()
  {
    return entry;
  }

  public void setEntry(DiaryEntry entry)
  {
    this.entry = entry;
  }

  public Medication getMedication()
  {
    return medication;
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

  public String getHowOften()
  {
    return howOften;
  }

  public void setHowOften(String howOften)
  {
    this.howOften = howOften;
  }

  public boolean getPainDecrease()
  {
    return painDecrease;
  }

  public void setPainDecrease(boolean painDecrease)
  {
    this.painDecrease = painDecrease;
  }

  public void setMedication(Medication medication)
  {
    this.medication = medication;
  }

  public static void save(DiaryMedicationEntry med)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(med);

      session.getTransaction().commit();
    }
  }
  
  @SuppressWarnings("unchecked")
  public static List<DiaryMedicationEntry> list()
  {

    List<DiaryMedicationEntry> meds;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      meds = session.createQuery("from UserMedication").list();
    }
    return meds;
  }
}
