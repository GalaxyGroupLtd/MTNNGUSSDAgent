/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.models;

/**
 *
 * @author manzi
 */
public class UssdRequest {
    private String msisdn;
    private String sessionId;
    private String spid;
    private String Input; 
    private Integer newRequest; // 1- for  YES  and 0 for NO

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
     * @return the Input
     */
    public String getInput() {
        return Input;
    }

    /**
     * @param Input the Input to set
     */
    public void setInput(String Input) {
        this.Input = Input;
    }

    /**
     * @return the newRequest
     */
    public Integer getNewRequest() {
        return newRequest;
    }

    /**
     * @param newRequest the newRequest to set
     */
    public void setNewRequest(Integer newRequest) {
        this.newRequest = newRequest;
    }
}
