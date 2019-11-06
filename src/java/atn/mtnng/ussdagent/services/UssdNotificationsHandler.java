/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.services;

import atn.mtnng.ussdagent.bizlogic.SdpRequestManager;
import atn.mtnng.ussdagent.bizlogic.UssdAppsManager;
import atn.mtnng.ussdagent.models.UssdRequest;
import atn.mtnng.ussdagent.models.UssdResponse;
import atn.mtnng.ussdagent.utilities.CommonLibrary;
import atn.mtnng.ussdagent.utilities.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author manzi
 */
@Path("UssdNotificationsHandler")
public class UssdNotificationsHandler {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UssdNotificationsHandler
     */
    public UssdNotificationsHandler() {
    }

    @EJB 
            SdpRequestManager sdpRequestManager;
    
    @POST
    @Path("/")
    @Produces({"text/xml", "application/xml"})
    public String handler(String input){
        try{
            Log.info(this.getClass(),"INCOMING REQUEST"+ input);
           return sdpRequestManager.notifyUssdReception(input);
        }catch(Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            Log.trace(UssdAppsManager.class,"sendUnexistingRouteMessage: "+  e.getMessage() + "\nTRACE: "+ message);
            return null;
        }
    }
    
    @POST
    @Path("/startNotification")
    public String startNotification(String spId){
        try{
           return sdpRequestManager.startUssdNOtification(spId);
        }catch(Exception ex){
            return ex.getMessage();
        }
    }
    
    @POST
    @Path("/stopNotification")
    public String stopNotification(String spId){
        try{
           return sdpRequestManager.stopUssdNOtification(spId);
        }catch(Exception ex){
            return ex.getMessage();
        }
    }
    
    
    @POST
    @Path("/testussd")
    @Produces({"application/json"})
    public UssdResponse testUssd(String input){
            UssdRequest ussdRequest=(UssdRequest)CommonLibrary.unmarshalling(input, UssdRequest.class, "json");
            
            UssdResponse ussdResponse=new UssdResponse();
            ussdResponse.setMessage("ATN Vouchers system coming up soon, choose \n 1. Ready \n 2.Not ready");
            ussdResponse.setMsisdn(ussdRequest.getMsisdn());
            ussdResponse.setFreeFlow("C");
            ussdResponse.setSessionId(ussdRequest.getSessionId());
            ussdResponse.setSpid(ussdRequest.getSpid());
            
            if(ussdRequest.getNewRequest()==0){
                if(ussdRequest.getInput().equals("1"))
                    ussdResponse.setMessage("Good you are ready!");
                else
                    if(ussdRequest.getInput().equals("2"))
                        ussdResponse.setMessage("Bad, you are not ready!");
                    else
                        ussdResponse.setMessage("Invalid choice!");
                
                ussdResponse.setFreeFlow("B");
            }
            
            return ussdResponse;
    }
}
