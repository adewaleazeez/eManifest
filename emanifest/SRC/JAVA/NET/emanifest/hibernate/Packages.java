package net.emanifest.hibernate;
// Generated Dec 5, 2010 1:42:23 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Packages generated by hbm2java
 */
@Entity
@Table(name="packages"
    ,catalog="emanifest"
)
public class Packages  implements java.io.Serializable {


     private Integer serialno;
     private String code;
     private String description;

    public Packages() {
    }

    public Packages(String code, String description) {
       this.code = code;
       this.description = description;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="serialno", unique=true, nullable=false)
    public Integer getSerialno() {
        return this.serialno;
    }
    
    public void setSerialno(Integer serialno) {
        this.serialno = serialno;
    }
    
    @Column(name="code", length=10)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }




}


