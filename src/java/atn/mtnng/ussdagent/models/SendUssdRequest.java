/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.models;

/**
 *
 * @author manzi
 * 
 *  */
public class SendUssdRequest {
    private Integer msgType;
    private String senderCB;
    private String receiveCB;
    private Integer ussdOpType;
    private String msIsdn;
    private String serviceCode;
    private String codeScheme;
    private String ussdString;

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
    public Integer getUssdOpType() {
        return ussdOpType;
    }

    /**
     * @param ussdOpType the ussdOpType to set
     */
    public void setUssdOpType(Integer ussdOpType) {
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
}
