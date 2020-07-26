package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_study_pmnt")
public class UserStudyPayments
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  protected Long id;
  @ManyToOne
  @Type(type = "uuid-char")
  @JoinColumn(name="user_id", nullable=false)
  protected User user;
  @ManyToOne
  @JoinColumn(name = "study_id", nullable = false)
  protected Study     study;
  @Column(name="pmnt_amt")
  protected Double paymentAmount;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "pmnt_date")
  protected Timestamp paymentDate;
  
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
}
