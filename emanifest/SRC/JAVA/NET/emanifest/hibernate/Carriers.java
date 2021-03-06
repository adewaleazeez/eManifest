package net.emanifest.hibernate;
// Generated Dec 5, 2010 1:42:23 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Carriers generated by hbm2java
 */
@Entity
@Table(name="carriers"
    ,catalog="emanifest"
)
public class Carriers  implements java.io.Serializable {


     private Integer serialno;
     private String code;
     private String name;
     private String address;

    public Carriers() {
    }

    public Carriers(String code, String name, String address) {
       this.code = code;
       this.name = name;
       this.address = address;
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
    
    @Column(name="name", length=100)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="address")
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }




}


