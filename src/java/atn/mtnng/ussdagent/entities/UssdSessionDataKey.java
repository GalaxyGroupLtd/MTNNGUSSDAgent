/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ismaelnzamutuma
 */
public class UssdSessionDataKey implements Serializable{
    
    private String msisdn;
    private String sessionId;
    
    
    public UssdSessionDataKey (){
        
    }
    
    public UssdSessionDataKey (String msisdn,String sessionid)
    {
        this.msisdn=msisdn;
        this.sessionId=sessionid;
    }
    @Override
    public boolean equals(Object object)
    {
        
        if (!(object instanceof UssdSessionData)) {
            return false;
        }
        UssdSessionDataKey other = (UssdSessionDataKey) object;
        if ((this.msisdn==null && other.getMsisdn() != null)  || (this.msisdn != null && !this.msisdn.equals(other.getMsisdn()))) {
            return false;
            
        }
        if ((this.sessionId==null && other.getSessionId() != null)  || (this.sessionId != null && !this.sessionId.equals(other.getSessionId()))) {
            return false;
        }
        return true;
        
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (msisdn != null ? msisdn.hashCode() : 0);
        hash += (sessionId != null ? sessionId.hashCode() : 0);
        return hash;
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
}
