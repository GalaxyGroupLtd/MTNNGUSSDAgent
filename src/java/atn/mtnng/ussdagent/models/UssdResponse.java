/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.models;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author manzi
 */
public class UssdResponse {
    private String msisdn; // return as received
    private String sessionId; //return as received
    private String spid;  //return the received
    private String message;
    private String freeFlow; // C - continue, B- block

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

    /**
     * @return the spid
     */
    public String getSpid() {
        return spid;
    }

    /**
     * @param spid the spid to set
     */
    public void setSpid(String spid) {
        this.spid = spid;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the freeFlow
     */
    public String getFreeFlow() {
        return freeFlow;
    }

    /**
     * @param freeFlow the freeFlow to set
     */
    public void setFreeFlow(String freeFlow) {
        this.freeFlow = freeFlow;
    }

   
}
