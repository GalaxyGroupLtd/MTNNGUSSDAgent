/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author manzi
 * 
 * 
 * <ns1:NotifySOAPHeader xmlns:ns1="http://www.huawei.com.cn/schema/common/v2_1"> 
	<ns1:spRevId>35000001</ns1:spRevId> 
	<ns1:spRevpassword>206D88BB7F3D154B130DD6E1E0B8828B</ns1:spRevpassword> 
	<ns1:spId>000201</ns1:spId> 
	<ns1:serviceId>35000001000029</ns1:serviceId> 
	<ns1:timeStamp>20100731064245</ns1:timeStamp>
	<ns1:linkid>12345678901111</ns1:linkid> 
	<ns1:traceUniqueID>404092403801104031047140004003</ns1:traceUniqueID> 
</ns1:NotifySOAPHeader>
 */

@XmlType(namespace="http://www.huawei.com.cn/schema/common/v2_1")
@XmlRootElement(name = "NotifySOAPHeader")
@XmlAccessorType (XmlAccessType.FIELD)
public class NotifySOAPHeader {
    @XmlElement
    private String spRevId;
    @XmlElement
    private String spRevpassword;
    @XmlElement
    private String spId;
    @XmlElement
    private String serviceId;
    @XmlElement
    private String timeStamp;
    @XmlElement
    private String linkid;
    @XmlElement
    private String traceUniqueID;

    /**
     * @return the spRevId
     */
    public String getSpRevId() {
        return spRevId;
    }

    /**
     * @param spRevId the spRevId to set
     */
    public void setSpRevId(String spRevId) {
        this.spRevId = spRevId;
    }

    /**
     * @return the spRevpassword
     */
    public String getSpRevpassword() {
        return spRevpassword;
    }

    /**
     * @param spRevpassword the spRevpassword to set
     */
    public void setSpRevpassword(String spRevpassword) {
        this.spRevpassword = spRevpassword;
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
     * @return the timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the linkid
     */
    public String getLinkid() {
        return linkid;
    }

    /**
     * @param linkid the linkid to set
     */
    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    /**
     * @return the traceUniqueID
     */
    public String getTraceUniqueID() {
        return traceUniqueID;
    }

    /**
     * @param traceUniqueID the traceUniqueID to set
     */
    public void setTraceUniqueID(String traceUniqueID) {
        this.traceUniqueID = traceUniqueID;
    }
}
