/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.bizlogic;

import atn.mtnng.ussdagent.bizlogic.entitiesFacades.SdpSpProfileFacade;
import atn.mtnng.ussdagent.entities.SdpSpProfile;
import atn.mtnng.ussdagent.models.NotifyUssdReceptionRequest;
import atn.mtnng.ussdagent.models.RequestSoapHeader;
import atn.mtnng.ussdagent.models.SendUssdRequest;
import atn.mtnng.ussdagent.models.UssdRequest;
import atn.mtnng.ussdagent.models.UssdResponse;
import atn.mtnng.ussdagent.utilities.CommonLibrary;
import atn.mtnng.ussdagent.utilities.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author manzi
 */
@Stateless
public class UssdAppsManager {
    
    @EJB
            SdpRequestManager sdpRequestManager;
    @EJB
            SdpSpProfileFacade sdpSpProfileFacade;
    
    public void getResponseFromAppSend2Sdp(UssdRequest ussdRequest,NotifyUssdReceptionRequest notifyUssdReceptionRequest,String url){
        try{
            String request= CommonLibrary.marshalling(ussdRequest, "json");
            Log.info(this.getClass(),url+"\n"+ request);
            Response response = CommonLibrary.sendRESTRequest(url,request,MediaType.APPLICATION_JSON, "POST");
            String respStr=response.readEntity(String.class);
            Log.info(this.getClass(), respStr);            
            UssdResponse ussdResponse=(UssdResponse)CommonLibrary.unmarshalling(respStr, UssdResponse.class, "json");            
            
            //String OA,String FA,String LinkId
            SdpSpProfile sdpSpProfile=sdpSpProfileFacade.find(notifyUssdReceptionRequest.getHeader().getSpId());
            RequestSoapHeader requestSoapHeader=new RequestSoapHeader(
                    sdpSpProfile
                    ,ussdResponse.getMsisdn()
                    ,ussdResponse.getMsisdn()
                    ,notifyUssdReceptionRequest.getHeader().getLinkid());
            
            
            
            SendUssdRequest sendUssdRequest= new SendUssdRequest();
            sendUssdRequest.setCodeScheme(notifyUssdReceptionRequest.getBody().getCodeScheme());
            sendUssdRequest.setMsIsdn(ussdResponse.getMsisdn());
            
            if(ussdResponse.getFreeFlow().equals("C")){
                sendUssdRequest.setMsgType(1);  //
                sendUssdRequest.setUssdOpType(1);//
            }
            
            if(ussdResponse.getFreeFlow().equals("B")){
                sendUssdRequest.setMsgType(2); // 
                sendUssdRequest.setUssdOpType(3);//response
            }
            
            sendUssdRequest.setReceiveCB(ussdResponse.getSessionId());
            sendUssdRequest.setSenderCB(ussdResponse.getSessionId());
            sendUssdRequest.setServiceCode(notifyUssdReceptionRequest.getBody().getServiceCode());
            sendUssdRequest.setUssdString(ussdResponse.getMessage());
            
            request=sdpRequestManager.buildUssdSendRequest(requestSoapHeader, sendUssdRequest);
            
            url=sdpSpProfile.getSdpSendUssdEndPoint();
            Log.info(this.getClass(),"USSD SEND REQUEST | "+url+"| "+ request);
            response = CommonLibrary.sendRESTRequest(url,request,MediaType.APPLICATION_XML, "POST");
            respStr=response.readEntity(String.class);
            Log.info(this.getClass(),"USSD SEND RESPONSE: |"+ respStr);
        }catch(Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            Log.trace(UssdAppsManager.class, "getResponseFromAppSend2Sdp: "+ e.getMessage() + " | TRACE: "+ message);
        }
        
    }
    
    public void sendUnexistingRouteMessage(UssdRequest ussdRequest,NotifyUssdReceptionRequest notifyUssdReceptionRequest){
        
        try{
            //        String request= CommonLibrary.marshalling(ussdRequest, "json");
            //        Response response = postRequest(url,request,null,MediaType.APPLICATION_JSON) ;
            //
            //        String respStr=response.readEntity(String.class);
            
            UssdResponse ussdResponse=new UssdResponse();
            ussdResponse.setMessage("ATN: Invalid input !");
            ussdResponse.setMsisdn(ussdRequest.getMsisdn());
            ussdResponse.setFreeFlow("B");  // response
            ussdResponse.setSessionId(ussdRequest.getSessionId());
            ussdResponse.setSpid(ussdRequest.getSpid());
            
            //String OA,String FA,String LinkId
            SdpSpProfile sdpSpProfile=sdpSpProfileFacade.find(notifyUssdReceptionRequest.getHeader().getSpId());
            RequestSoapHeader requestSoapHeader=new RequestSoapHeader(
                    sdpSpProfile
                    ,ussdResponse.getMsisdn()
                    ,ussdResponse.getMsisdn()
                    ,notifyUssdReceptionRequest.getHeader().getLinkid());
            
            
            
            SendUssdRequest sendUssdRequest= new SendUssdRequest();
            sendUssdRequest.setCodeScheme(notifyUssdReceptionRequest.getBody().getCodeScheme());
            sendUssdRequest.setMsIsdn(ussdResponse.getMsisdn());
            sendUssdRequest.setMsgType(2);  //Response
            sendUssdRequest.setUssdOpType(3);  //last message
            sendUssdRequest.setReceiveCB(ussdResponse.getSessionId());
            sendUssdRequest.setSenderCB(ussdResponse.getSessionId());
            sendUssdRequest.setServiceCode(notifyUssdReceptionRequest.getBody().getServiceCode());
            sendUssdRequest.setUssdString(ussdResponse.getMessage());
            
            
            String request=sdpRequestManager.buildUssdSendRequest(requestSoapHeader, sendUssdRequest);
            
            String url=url=sdpSpProfile.getSdpSendUssdEndPoint();
            Log.info(this.getClass(),"\n USSD SEND REQUEST:\n"+url+"\n"+ request);
            Response response = CommonLibrary.sendRESTRequest(url,request,MediaType.APPLICATION_XML, "POST");
            String respStr=response.readEntity(String.class);
            Log.info(this.getClass(),"USSD SEND RESPONSE:\n"+ respStr);
            
        }catch(Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            Log.trace(UssdAppsManager.class,"sendUnexistingRouteMessage: "+  e.getMessage() + "\nTRACE: "+ message);
        }
    }
    
}
