/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author manzi
 */
@Entity
@Table(name="sdp_sp_profiles")
public class SdpSpProfile implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "sp_id", length = 50)
    private String spId;
    
    @Column(name = "service_id", length = 50)
    private String serviceId;
    
    @Column(name = "password", length = 255)
    private String password;
    
    @Column(name = "code", length = 50)
    private String code;
    
    @Column(name = "local_endpoint", length = 255)
    private String localEndPoint;
    
    @Column(name = "sdp_notification_endpoint", length = 255)
    private String sdpNotificationEndPoint;
    
    @Column(name = "sdp_send_ussd_endpoint", length = 255)
    private String sdpSendUssdEndPoint;
    
    @Column(name = "codescheme", length = 50)
    private String codescheme;
    
    @Column(name = "correrator", length = 50)
    private String correrator;
    
    @Column(name = "interface_name", length = 100)
    private String InterfaceName;
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spId != null ? spId.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SdpSpProfile)) {
            return false;
        }
        SdpSpProfile other = (SdpSpProfile) object;
        if ((this.spId == null && other.spId != null) || (this.spId != null && !this.spId.equals(other.spId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "atn.mtnng.ussdagent.entities.SdpSpProfile[ spId=" + spId + " ]";
    }
    
    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
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
    
    /**
     * @return the serviceId
     */
    public String getServiceId() {
        return serviceId;
    }
    
    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return the localEndPoint
     */
    public String getLocalEndPoint() {
        return localEndPoint;
    }
    
    /**
     * @param localEndPoint the localEndPoint to set
     */
    public void setLocalEndPoint(String localEndPoint) {
        this.localEndPoint = localEndPoint;
    }
    
   
    /**
     * @return the codescheme
     */
    public String getCodescheme() {
        return codescheme;
    }
    
    /**
     * @param codescheme the codescheme to set
     */
    public void setCodescheme(String codescheme) {
        this.codescheme = codescheme;
    }
    
    /**
     * @return the correrator
     */
    public String getCorrerator() {
        return correrator;
    }
    
    /**
     * @param correrator the correrator to set
     */
    public void setCorrerator(String correrator) {
        this.correrator = correrator;
    }
    
    /**
     * @return the InterfaceName
     */
    public String getInterfaceName() {
        return InterfaceName;
    }
    
    /**
     * @param InterfaceName the InterfaceName to set
     */
    public void setInterfaceName(String InterfaceName) {
        this.InterfaceName = InterfaceName;
    }

    /**
     * @return the sdpNotificationEndPoint
     */
    public String getSdpNotificationEndPoint() {
        return sdpNotificationEndPoint;
    }

    /**
     * @param sdpNotificationEndPoint the sdpNotificationEndPoint to set
     */
    public void setSdpNotificationEndPoint(String sdpNotificationEndPoint) {
        this.sdpNotificationEndPoint = sdpNotificationEndPoint;
    }

    /**
     * @return the sdpSendUssdEndPoint
     */
    public String getSdpSendUssdEndPoint() {
        return sdpSendUssdEndPoint;
    }

    /**
     * @param sdpSendUssdEndPoint the sdpSendUssdEndPoint to set
     */
    public void setSdpSendUssdEndPoint(String sdpSendUssdEndPoint) {
        this.sdpSendUssdEndPoint = sdpSendUssdEndPoint;
    }

        
}
