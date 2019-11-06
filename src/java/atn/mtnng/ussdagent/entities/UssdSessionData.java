/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author manzi
 */
@Entity
@Table(name="ussd_sessions_data")
@IdClass(UssdSessionDataKey.class)
public class UssdSessionData implements Serializable {
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "msisdn", length = 50)
    private String msisdn;
    
    
    @Id
    @Column(name = "session_id", length = 50)
    private String sessionId;
    
  
    @Column(name = "short_code", length = 50)
    private String shortCode;
    
    @Column(name = "sp_id", length = 50)
    private String spId;
    
    
    @Column(name = "service_code", length = 50)
    private String serviceCode;  // should normally be the ussd short code
    
    
    private Integer sessionType; //1 - User initiated  -0 third party initiated
    
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date initTime;
    
    @Column(name = "code_scheme", length = 50)
    private String codeScheme;  // 
    
     @Column(name = "dest_url", length = 255)
    private String dest_url;  // 
    
    
    @Override
   public boolean equals(Object object)
   {
           
        if (!(object instanceof UssdSessionData)) {
            return false;
        }
        UssdSessionData other = (UssdSessionData) object;
        if ((this.msisdn==null && other.getMsisdn()!= null)  || (this.msisdn != null && !this.msisdn.equals(other.getMsisdn()))) {
            return false;
            
        }
        if ((this.shortCode==null && other.getShortCode() != null)  || (this.shortCode != null && !this.shortCode.equals(other.getShortCode()))) {
            return false;
        }
         if ((this.msisdn==null && other.getSessionId() != null)  || (this.msisdn != null && !this.msisdn.equals(other.getSessionId()))) {
            return false;
        }
        return true;
       
   }
   
   @Override
   public int hashCode()
   {
        int hash = 0;
        hash += (msisdn != null ? msisdn.hashCode() : 0);
        hash += (shortCode != null ? shortCode.hashCode() : 0);
        hash += (sessionId != null ? sessionId.hashCode() : 0);
        return hash;
   }

    @Override
    public String toString() {
        return "atn.mtnng.ussdagent.entities.UssdSessionData[ msisdn=" + msisdn + " | shortCode="+shortCode+" sessionId+"+sessionId+" ]";
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return the msisdn
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * @param msisdn the msisdn to set
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * @return the shortCode
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * @param shortCode the shortCode to set
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the serviceCode
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * @param serviceCode the serviceCode to set
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * @return the sessionType
     */
    public Integer getSessionType() {
        return sessionType;
    }

    /**
     * @param sessionType the sessionType to set
     */
    public void setSessionType(Integer sessionType) {
        this.sessionType = sessionType;
    }

    /**
     * @return the initTime
     */
    public Date getInitTime() {
        return initTime;
    }

    /**
     * @param initTime the initTime to set
     */
    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }

    /**
     * @return the codeScheme
     */
    public String getCodeScheme() {
        return codeScheme;
    }

    /**
     * @param codeScheme the codeScheme to set
     */
    public void setCodeScheme(String codeScheme) {
        this.codeScheme = codeScheme;
    }

    /**
     * @return the dest_url
     */
    public String getDest_url() {
        return dest_url;
    }

    /**
     * @param dest_url the dest_url to set
     */
    public void setDest_url(String dest_url) {
        this.dest_url = dest_url;
    }

    /**
     * @return the spId
     */
    public String getSpId() {
        return spId;
    }

    /**
     * @param spId the spId to set
     */
    public void setSpId(String spId) {
        this.spId = spId;
    }
    
   
}
