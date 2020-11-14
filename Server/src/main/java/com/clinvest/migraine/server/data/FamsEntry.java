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
import org.hibernate.annotations.Type;
import org.hibernate.query.Query;



@Entity
@Table(name = "fams")
public class FamsEntry
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;
  @ManyToOne
  @Type(type = "uuid-char")
  @JoinColumn(name="user_id", nullable=false)
  protected User user;
  @ManyToOne
  @JoinColumn(name = "study_id", nullable = false)
  protected Study     study;
  @Column(name = "Q1")
  protected Integer Q1;
  @Column(name = "Q2")
  protected Integer Q2;
  @Column(name = "Q3")
  protected Integer Q3;
  @Column(name = "Q4")
  protected Integer Q4;
  @Column(name = "Q5")
  protected Integer Q5;
  @Column(name = "Q6")
  protected Integer Q6;
  @Column(name = "Q7")
  protected Integer Q7;
  @Column(name = "Q8")
  protected Integer Q8;
  @Column(name = "Q9")
  protected Integer Q9;
  @Column(name = "Q10")
  protected Integer Q10;
  @Column(name = "Q11")
  protected Integer Q11;
  @Column(name = "Q12")
  protected Integer Q12;
  @Column(name = "Q13")
  protected Integer Q13;
  @Column(name = "Q14")
  protected Integer Q14;
  @Column(name = "Q15")
  protected Integer Q15;
  @Column(name = "Q16")
  protected Integer Q16;
  @Column(name = "Q17")
  protected Integer Q17;
  @Column(name = "Q18")
  protected Integer Q18;
  @Column(name = "Q19")
  protected Integer Q19;
  @Column(name = "Q20")
  protected Integer Q20;
  @Column(name = "Q21")
  protected Integer Q21;
  @Column(name = "Q22")
  protected Integer Q22;
  @Column(name = "Q23")
  protected Integer Q23;
  @Column(name = "Q24")
  protected Integer Q24;
  @Column(name = "Q25")
  protected Integer Q25;
  @Column(name = "Q26")
  protected Integer Q26;
  @Column(name = "Q27")
  protected Integer Q27;

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

  public Study getStudy()
  {
    return study;
  }

  public void setStudy(Study study)
  {
    this.study = study;
  }

  public Timestamp getModified()
  {
    return modified;
  }

  public void setModified(Timestamp modified)
  {
    this.modified = modified;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Integer getQ1()
  {
    return Q1;
  }

  public void setQ1(Integer q1)
  {
    Q1 = q1;
  }

  public Integer getQ2()
  {
    return Q2;
  }

  public void setQ2(Integer q2)
  {
    Q2 = q2;
  }

  public Integer getQ3()
  {
    return Q3;
  }

  public void setQ3(Integer q3)
  {
    Q3 = q3;
  }

  public Integer getQ4()
  {
    return Q4;
  }

  public void setQ4(Integer q4)
  {
    Q4 = q4;
  }

  public Integer getQ5()
  {
    return Q5;
  }

  public void setQ5(Integer q5)
  {
    Q5 = q5;
  }

  public Integer getQ6()
  {
    return Q6;
  }

  public void setQ6(Integer q6)
  {
    Q6 = q6;
  }

  public Integer getQ7()
  {
    return Q7;
  }

  public void setQ7(Integer q7)
  {
    Q7 = q7;
  }

  public Integer getQ8()
  {
    return Q8;
  }

  public void setQ8(Integer q8)
  {
    Q8 = q8;
  }

  public Integer getQ9()
  {
    return Q9;
  }

  public void setQ9(Integer q9)
  {
    Q9 = q9;
  }

  public Integer getQ10()
  {
    return Q10;
  }

  public void setQ10(Integer q10)
  {
    Q10 = q10;
  }

  public Integer getQ11()
  {
    return Q11;
  }

  public void setQ11(Integer q11)
  {
    Q11 = q11;
  }

  public Integer getQ12()
  {
    return Q12;
  }

  public void setQ12(Integer q12)
  {
    Q12 = q12;
  }

  public Integer getQ13()
  {
    return Q13;
  }

  public void setQ13(Integer q13)
  {
    Q13 = q13;
  }

  public Integer getQ14()
  {
    return Q14;
  }

  public void setQ14(Integer q14)
  {
    Q14 = q14;
  }

  public Integer getQ15()
  {
    return Q15;
  }

  public void setQ15(Integer q15)
  {
    Q15 = q15;
  }

  public Integer getQ16()
  {
    return Q16;
  }

  public void setQ16(Integer q16)
  {
    Q16 = q16;
  }

  public Integer getQ17()
  {
    return Q17;
  }

  public void setQ17(Integer q17)
  {
    Q17 = q17;
  }

  public Integer getQ18()
  {
    return Q18;
  }

  public void setQ18(Integer q18)
  {
    Q18 = q18;
  }

  public Integer getQ19()
  {
    return Q19;
  }

  public void setQ19(Integer q19)
  {
    Q19 = q19;
  }

  public Integer getQ20()
  {
    return Q20;
  }

  public void setQ20(Integer q20)
  {
    Q20 = q20;
  }

  public Integer getQ21()
  {
    return Q21;
  }

  public void setQ21(Integer q21)
  {
    Q21 = q21;
  }

  public Integer getQ22()
  {
    return Q22;
  }

  public void setQ22(Integer q22)
  {
    Q22 = q22;
  }

  public Integer getQ23()
  {
    return Q23;
  }

  public void setQ23(Integer q23)
  {
    Q23 = q23;
  }

  public Integer getQ24()
  {
    return Q24;
  }

  public void setQ24(Integer q24)
  {
    Q24 = q24;
  }

  public Integer getQ25()
  {
    return Q25;
  }

  public void setQ25(Integer q25)
  {
    Q25 = q25;
  }

  public Integer getQ26()
  {
    return Q26;
  }

  public void setQ26(Integer q26)
  {
    Q26 = q26;
  }

  public Integer getQ27()
  {
    return Q27;
  }

  public void setQ27(Integer q27)
  {
    Q27 = q27;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Timestamp getCreated()
  {
    return created;
  }

  public void setCreated(Timestamp created)
  {
    this.created = created;
  }

  public static void save(FamsEntry fams)
  {

    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      session.beginTransaction();

      session.save(fams);

      session.getTransaction().commit();
    }
  }
  
  @SuppressWarnings("unchecked")
  public static FamsEntry getLastEntry(User user)
  {
    FamsEntry retval = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession())
    {
      Query<FamsEntry> query = session.createQuery("FROM FamsEntry WHERE user = :user ORDER BY created DESC");
      query.setMaxResults(1);
      query.setParameter("user", user);

      List<FamsEntry> list = query.list();
      if (null != list && list.size() > 0)
      {
        retval = list.get(0);
      }
    }
    return retval;
  }

}
