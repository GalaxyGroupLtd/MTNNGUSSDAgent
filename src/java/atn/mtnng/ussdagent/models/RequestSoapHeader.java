/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.models;

import atn.mtnng.ussdagent.bizlogic.entitiesFacades.SdpSpProfileFacade;
import atn.mtnng.ussdagent.entities.SdpSpProfile;
import atn.mtnng.ussdagent.utilities.CommonLibrary;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.ejb.EJB;

/**
 *
 * @author manzi
 */
public class RequestSoapHeader {
    private String spId;
    private String spPassword;
    private String serviceId;
    private String timeStamp;
    private String OA;
    private String FA;
    private String linkId; 

    /**
     * @return the spId
     */
    
    @EJB
            SdpSpProfileFacade sdpSpProfileFacade;
            
    public  RequestSoapHeader(){
      
    }
    
    public RequestSoapHeader(SdpSpProfile sdpSpProfile){
        this.spId=sdpSpProfile.getSpId();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date currentTime=new Date();
        String timeStamp=sdf.format(currentTime);
        this.spPassword=CommonLibrary.md5hashing(spId+sdpSpProfile.getPassword()+timeStamp);
        this.serviceId=sdpSpProfile.getServiceId();
        this.timeStamp=timeStamp; 
        
    }
    
    public RequestSoapHeader(SdpSpProfile sdpSpProfile,String OA,String FA,String linkId){
        this.spId=sdpSpProfile.getSpId();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date currentTime=new Date();
        String timeStamp=sdf.format(currentTime);
        this.spPassword=CommonLibrary.md5hashing(this.spId+sdpSpProfile.getPassword()+timeStamp);
        this.serviceId=sdpSpProfile.getServiceId();
        this.timeStamp=timeStamp; 
        this.OA=OA;
        this.FA=FA;
        this.linkId=linkId;
        
    }
    
   
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
     * @return the spPassword
     */
    public String getSpPassword() {
        return spPassword;
    }

    /**
     * @param spPassword the spPassword to set
     */
    public void setSpPassword(String spPassword) {
        this.spPassword = spPassword;
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
     * @return the OA
     */
    public String getOA() {
        return OA;
    }

    /**
     * @param OA the OA to set
     */
    public void setOA(String OA) {
        this.OA = OA;
    }

    /**
     * @return the FA
     */
    public String getFA() {
        return FA;
    }

    /**
     * @param FA the FA to set
     */
    public void setFA(String FA) {
        this.FA = FA;
    }

    /**
     * @return the linkId
     */
    public String getLinkId() {
        return linkId;
    }

    /**
     * @param linkId the linkId to set
     */
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }


}
