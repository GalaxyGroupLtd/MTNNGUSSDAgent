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
 */

/*
<ns2:notifyUssdReception xmlns:ns2="http://www.csapi.org/schema/parlayx/ussd/notification/v1_0/local"> 
	<ns2:msgType>0</ns2:msgType> 
	<ns2:senderCB>320207133</ns2:senderCB> 
	<ns2:receiveCB>0xFFFFFFFF</ns2:receiveCB> 
	<ns2:ussdOpType>1</ns2:ussdOpType> 
	<ns2:msIsdn>8613699991234</ns2:msIsdn> 
	<ns2:serviceCode>2929</ns2:serviceCode> 
	<ns2:codeScheme>17</ns2:codeScheme>
	<ns2:ussdString>*10086*01#</ns2:ussdString> 
	<ns2:extenionInfo> 
	<item> 
		<key>
		</key> 
		<value>
		</value> 
	</item> 
	</ns2:extensionInfo> 
</ns2:notifyUssdReception>
*/

@XmlType(namespace="http://www.csapi.org/schema/parlayx/ussd/notification/v1_0/local")
@XmlRootElement(name = "notifyUssdReception")
@XmlAccessorType (XmlAccessType.FIELD)
public class NotifyUssdReception {
    @XmlElement
    private Integer msgType;
    @XmlElement
    private String senderCB;
    @XmlElement
    private String receiveCB;
    @XmlElement
    private String ussdOpType;
    @XmlElement
    private String msIsdn;
    @XmlElement
    private String serviceCode;
    @XmlElement
    private String codeScheme;
    @XmlElement
    private String ussdString;
    @XmlElement
    private Item extenionInfo;

    /**
     * @return the msgType
     */
    public Integer getMsgType() {
        return msgType;
    }

    /**
     * @param msgType the msgType to set
     */
    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    /**
     * @return the senderCB
     */
    public String getSenderCB() {
        return senderCB;
    }

    /**
     * @param senderCB the senderCB to set
     */
    public void setSenderCB(String senderCB) {
        this.senderCB = senderCB;
    }

    /**
     * @return the receiveCB
     */
    public String getReceiveCB() {
        return receiveCB;
    }

    /**
     * @param receiveCB the receiveCB to set
     */
    public void setReceiveCB(String receiveCB) {
        this.receiveCB = receiveCB;
    }

    /**
     * @return the ussdOpType
     */
    public String getUssdOpType() {
        return ussdOpType;
    }

    /**
     * @param ussdOpType the ussdOpType to set
     */
    public void setUssdOpType(String ussdOpType) {
        this.ussdOpType = ussdOpType;
    }

    /**
     * @return the msIsdn
     */
    public String getMsIsdn() {
        return msIsdn;
    }

    /**
     * @param msIsdn the msIsdn to set
     */
    public void setMsIsdn(String msIsdn) {
        this.msIsdn = msIsdn;
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
     * @return the ussdString
     */
    public String getUssdString() {
        return ussdString;
    }

    /**
     * @param ussdString the ussdString to set
     */
    public void setUssdString(String ussdString) {
        this.ussdString = ussdString;
    }

    /**
     * @return the extenionInfo
     */
    public Item getExtenionInfo() {
        return extenionInfo;
    }

    /**
     * @param extenionInfo the extenionInfo to set
     */
    public void setExtenionInfo(Item extenionInfo) {
        this.extenionInfo = extenionInfo;
    }
}
