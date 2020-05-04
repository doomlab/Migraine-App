package com.clinvest.migraine.server.data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.hibernate.query.Query;


@Entity
@Table(name = "fams")
public class FamsEntry 
{
    protected UUID userId;
    protected Timestamp created;
    protected int Q1;
    protected int Q2;
    protected int Q3;
    protected int Q4;
    protected int Q5;
    protected int Q6;
    protected int Q7;
    protected int Q8;
    protected int Q9;
    protected int Q10;
    protected int Q11;
    protected int Q12;
    protected int Q13;
    protected int Q14;
    protected int Q15;
    protected int Q16;
    protected int Q17;
    protected int Q18;
    protected int Q19;
    protected int Q20;
    protected int Q21;
    protected int Q22;
    protected int Q23;
    protected int Q24;
    protected int Q25;
    protected int Q26;
    protected int Q27;
    
    @Column(name = "Q1")
    public int getQ1()
    {
        return Q1;
    }
    public void setQ1(int q1)
    {
        Q1 = q1;
    }
    
    @Column(name = "Q2")
    public int getQ2()
    {
        return Q2;
    }
    public void setQ2(int q2)
    {
        Q2 = q2;
    }
    
    @Column(name = "Q3")
    public int getQ3()
    {
        return Q3;
    }
    public void setQ3(int q3)
    {
        Q3 = q3;
    }
    
    @Column(name = "Q4")
    public int getQ4()
    {
        return Q4;
    }
    public void setQ4(int q4)
    {
        Q4 = q4;
    }
    
    @Column(name = "Q5")
    public int getQ5()
    {
        return Q5;
    }
    public void setQ5(int q5)
    {
        Q5 = q5;
    }
    
    @Column(name = "Q6")
    public int getQ6()
    {
        return Q6;
    }
    public void setQ6(int q6)
    {
        Q6 = q6;
    }
    
    @Column(name = "Q7")
    public int getQ7()
    {
        return Q7;
    }
    public void setQ7(int q7)
    {
        Q7 = q7;
    }
    
    @Column(name = "Q8")
    public int getQ8()
    {
        return Q8;
    }
    public void setQ8(int q8)
    {
        Q8 = q8;
    }
    
    @Column(name = "Q9")
    public int getQ9()
    {
        return Q9;
    }
    public void setQ9(int q9)
    {
        Q9 = q9;
    }
    
    @Column(name = "Q10")
    public int getQ10()
    {
        return Q10;
    }
    public void setQ10(int q10)
    {
        Q10 = q10;
    }
    
    @Column(name = "Q11")
    public int getQ11()
    {
        return Q11;
    }
    public void setQ11(int q11)
    {
        Q11 = q11;
    }
    
    @Column(name = "Q12")
    public int getQ12()
    {
        return Q12;
    }
    public void setQ12(int q12)
    {
        Q12 = q12;
    }
    
    @Column(name = "Q13")
    public int getQ13()
    {
        return Q13;
    }
    public void setQ13(int q13)
    {
        Q13 = q13;
    }
    
    @Column(name = "Q14")
    public int getQ14()
    {
        return Q14;
    }
    public void setQ14(int q14)
    {
        Q14 = q14;
    }
    
    @Column(name = "Q15")
    public int getQ15()
    {
        return Q15;
    }
    public void setQ15(int q15)
    {
        Q15 = q15;
    }
    
    @Column(name = "Q16")
    public int getQ16()
    {
        return Q16;
    }
    public void setQ16(int q16)
    {
        Q16 = q16;
    }
    
    @Column(name = "Q17")
    public int getQ17()
    {
        return Q17;
    }
    public void setQ17(int q17)
    {
        Q17 = q17;
    }
    
    @Column(name = "Q18")
    public int getQ18()
    {
        return Q18;
    }
    public void setQ18(int q18)
    {
        Q18 = q18;
    }
    
    @Column(name = "Q19")
    public int getQ19()
    {
        return Q19;
    }
    public void setQ19(int q19)
    {
        Q19 = q19;
    }
    
    @Column(name = "Q20")
    public int getQ20()
    {
        return Q20;
    }
    public void setQ20(int q20)
    {
        Q20 = q20;
    }
    
    @Column(name = "Q21")
    public int getQ21()
    {
        return Q21;
    }
    public void setQ21(int q21)
    {
        Q21 = q21;
    }
    
    @Column(name = "Q22")
    public int getQ22()
    {
        return Q22;
    }
    public void setQ22(int q22)
    {
        Q22 = q22;
    }
    
    @Column(name = "Q23")
    public int getQ23()
    {
        return Q23;
    }
    public void setQ23(int q23)
    {
        Q23 = q23;
    }
    
    @Column(name = "Q24")
    public int getQ24()
    {
        return Q24;
    }
    public void setQ24(int q24)
    {
        Q24 = q24;
    }
    
    @Column(name = "Q25")
    public int getQ25()
    {
        return Q25;
    }
    public void setQ25(int q25)
    {
        Q25 = q25;
    }
    
    @Column(name = "Q26")
    public int getQ26()
    {
        return Q26;
    }
    public void setQ26(int q26)
    {
        Q26 = q26;
    }
    
    @Column(name = "Q27")
    public int getQ27()
    {
        return Q27;
    }
    public void setQ27(int q27)
    {
        Q27 = q27;
    }
    
    @Type(type = "uuid-char")
    @Column(name = "user_ID")
    public UUID getuserId()
    {
        return userId;
    }
    public void setId(UUID userId)
    {
        this.userId = userId;
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
    
}
