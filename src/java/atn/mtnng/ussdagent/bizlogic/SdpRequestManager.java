/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.bizlogic;


import atn.mtnng.ussdagent.bizlogic.entitiesFacades.SdpSpProfileFacade;
import atn.mtnng.ussdagent.bizlogic.entitiesFacades.UssdGwRouteFacade;
import atn.mtnng.ussdagent.bizlogic.entitiesFacades.UssdSessionDataFacade;
import atn.mtnng.ussdagent.entities.SdpSpProfile;
import atn.mtnng.ussdagent.entities.UssdRoute;
import atn.mtnng.ussdagent.entities.UssdRouteKey;
import atn.mtnng.ussdagent.entities.UssdSessionData;
import atn.mtnng.ussdagent.entities.UssdSessionDataKey;
import atn.mtnng.ussdagent.models.NotifySOAPHeader;
import atn.mtnng.ussdagent.models.NotifyUssdReception;
import atn.mtnng.ussdagent.models.NotifyUssdReceptionRequest;
import atn.mtnng.ussdagent.models.RequestSoapHeader;
import atn.mtnng.ussdagent.models.SendUssdRequest;
import atn.mtnng.ussdagent.models.UssdRequest;
import atn.mtnng.ussdagent.utilities.CommonLibrary;
import atn.mtnng.ussdagent.utilities.Log;
import atn.mtnng.ussdagent.utilities.SoapXmlRequest2tBeans;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author manzi
 */
@Stateless
public class SdpRequestManager {
    
    @EJB
            UssdSessionDataFacade ussdSessionDataFacade;
    @EJB
            UssdGwRouteFacade ussdGwRouteFacade;
    @EJB
            SdpSpProfileFacade sdpSpProfileFacade;
    @EJB
        UssdAppsManager ussdAppsManager;
    
    
   
    
   public String notifyUssdReception(String sopRequest){
        UssdSessionData ussdSessionData;
        UssdRoute ussdGwRoute=null;
        UssdRoute tmp_ussdGwRoute=null;
        SdpSpProfile sdpSpProfile;
        
        NotifyUssdReceptionRequest notifyUssdReceptionRequest=SoapXmlRequest2tBeans.extractNotifyUssdReceptionRequest(sopRequest);
        NotifyUssdReception NotifyUssdReception=notifyUssdReceptionRequest.getBody();
        NotifySOAPHeader notifySOAPHeader=notifyUssdReceptionRequest.getHeader();
                
        
          //build request
         UssdRequest ussdRequest= new UssdRequest();
        ussdRequest.setInput(NotifyUssdReception.getUssdString());
        ussdRequest.setMsisdn(NotifyUssdReception.getMsIsdn());
       
        ussdRequest.setSessionId(NotifyUssdReception.getSenderCB());
        ussdRequest.setSpid(notifySOAPHeader.getSpId());
        
        switch(NotifyUssdReception.getMsgType()){
            case 0:  //initial request
                // start by trying to retrieve request routing information
                ussdRequest.setNewRequest(1);
                
                String[] ussdInput={""};
                if(NotifyUssdReception.getUssdString().length()>1)
                    ussdInput = NotifyUssdReception.getUssdString().substring(1,NotifyUssdReception.getUssdString().length()-1).split("\\*");
                else
                    ussdInput[0]=NotifyUssdReception.getUssdString();
                
                String shortCode="";
                String ext="";
                
                if(ussdInput.length>0){
                    shortCode=ussdInput[0];
                    ussdGwRoute=ussdGwRouteFacade.find(new UssdRouteKey(shortCode,ext)); // initial possible route
                    //for the next loop to get the extension
                    if(ussdInput.length>1)
                        for(int i=1;i<ussdInput.length-1;i++){
                            if(i>1)
                                ext+="*"+ussdInput[i];
                            else
                                ext=ussdInput[i];
                            
                            tmp_ussdGwRoute=ussdGwRouteFacade.find(new UssdRouteKey(shortCode,ext));
                            
                            if(tmp_ussdGwRoute!=null){
                                ussdGwRoute=tmp_ussdGwRoute;
                            }
                        }
                    
                    if(ussdGwRoute==null){
                         Thread t=new Thread(new UnexistingRoute(ussdRequest,notifyUssdReceptionRequest));
                         t.start();
                         return getNotifyUssdReceptionResponse();
                    }
                    
                    if(ussdSessionDataFacade.find(new UssdSessionDataKey(NotifyUssdReception.getMsIsdn(),NotifyUssdReception.getSenderCB()))!=null){
                        
                    }
                    
                    //  save the session data
                    //Start by retrieving the sdp profile
                    
                    sdpSpProfile=sdpSpProfileFacade.find(notifySOAPHeader.getSpId());
                    ussdSessionData=new UssdSessionData();
                    ussdSessionData.setCodeScheme(sdpSpProfile.getCodescheme());
                    ussdSessionData.setInitTime(new Date());
                    ussdSessionData.setMsisdn(NotifyUssdReception.getMsIsdn());
                    ussdSessionData.setServiceCode(shortCode);
                    ussdSessionData.setSessionId(NotifyUssdReception.getSenderCB());
                    ussdSessionData.setSessionType(1);  // 1 for user initiated and 0 for 3pp initiated
                    ussdSessionData.setShortCode(shortCode);
                    ussdSessionData.setSpId(notifySOAPHeader.getSpId());
                    ussdSessionData.setDest_url(ussdGwRoute.getUrl());
                    ussdSessionDataFacade.create(ussdSessionData);
                    
                           
        Thread t=new Thread(new FwdUssdRequest(ussdRequest,notifyUssdReceptionRequest,ussdSessionData.getDest_url()));
        t.start();
        
                    return getNotifyUssdReceptionResponse();
                }
                break;
                
            case 1: // following request for existing session
                //find session data
                ussdRequest.setNewRequest(0);
                
                ussdSessionData=ussdSessionDataFacade.find(new UssdSessionDataKey(NotifyUssdReception.getMsIsdn(),NotifyUssdReception.getSenderCB()));
                
                if(ussdSessionData==null){
                    return getErrorNotifyUssdReceptionResponse();
                }
                
                Thread t=new Thread(new FwdUssdRequest(ussdRequest,notifyUssdReceptionRequest,ussdSessionData.getDest_url()));
                t.start();
                return getNotifyUssdReceptionResponse();
            default:
                return getNotifyUssdReceptionResponse();
        }
        
       
    
    
    return "";
}
    
   public String startUssdNOtification(String spId){
       try{
       SdpSpProfile sdpSpProfile=sdpSpProfileFacade.find(spId);
       RequestSoapHeader requestSoapHeader=new RequestSoapHeader(sdpSpProfile);
             
       
       String request=buildStartUssdNotificationRequest(requestSoapHeader,sdpSpProfile);
       String  url=sdpSpProfile.getSdpNotificationEndPoint();
      
       Log.info(this.getClass(),url+"START USSD REQUEST\n"+ request);
       Response response = CommonLibrary.sendRESTRequest(url, request, MediaType.APPLICATION_XML,"POST");
       String respStr=response.readEntity(String.class);
       Log.info(this.getClass(), "USSD REQUEST\n"+ respStr);
       
        return respStr;
       }catch(Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            Log.trace(UssdAppsManager.class, "startUssdNOtification: "+ e.getMessage() + "\nTRACE: "+ message);
            return e.getMessage() + "\nTRACE: "+ message;
       }
   }
   
   public String stopUssdNOtification(String spId){
       try{
           SdpSpProfile sdpSpProfile=sdpSpProfileFacade.find(spId);
           RequestSoapHeader requestSoapHeader=new RequestSoapHeader(sdpSpProfile);
           
           
           String request=buildStopUssdNotificationRequest(requestSoapHeader,sdpSpProfile);
           String  url=sdpSpProfile.getSdpNotificationEndPoint();
           
           Log.info(this.getClass(),url+"STOP USSD REQUEST\n"+ request);
           Response response = CommonLibrary.sendRESTRequest(url, request, MediaType.APPLICATION_XML,"POST");
           String respStr=response.readEntity(String.class);
           Log.info(this.getClass(),"STOP USSD RESPONSE\n"+ respStr);
       
        return respStr;
        }catch(Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            Log.trace(UssdAppsManager.class, "stopUssdNOtification: "+ e.getMessage() + "\nTRACE: "+ message);
            return e.getMessage() + "\nTRACE: "+ message;
       }
   }
   
    class FwdUssdRequest implements Runnable {
        private UssdRequest ussdRequest;
        private NotifyUssdReceptionRequest notifyUssdReceptionRequest;
        private String url;

        public FwdUssdRequest(UssdRequest ussdRequest,NotifyUssdReceptionRequest notifyUssdReceptionRequest,String url){
            this.ussdRequest=ussdRequest;
            this.notifyUssdReceptionRequest=notifyUssdReceptionRequest;
            this.url=url;
        }

            @Override
            public void run() {
                ussdAppsManager.getResponseFromAppSend2Sdp(ussdRequest,notifyUssdReceptionRequest, url);
            }
    }
    
    class UnexistingRoute implements Runnable {
        private UssdRequest ussdRequest;
        private NotifyUssdReceptionRequest notifyUssdReceptionRequest;
        private String url;

        public UnexistingRoute(UssdRequest ussdRequest,NotifyUssdReceptionRequest notifyUssdReceptionRequest){
            this.ussdRequest=ussdRequest;
            this.notifyUssdReceptionRequest=notifyUssdReceptionRequest;
            this.url=url;
        }

            @Override
            public void run() {
                ussdAppsManager.sendUnexistingRouteMessage(ussdRequest,notifyUssdReceptionRequest);
            }
    }

    public String buildStartUssdNotificationRequest(RequestSoapHeader requestSoapHeader,SdpSpProfile sdpSpProfile){


    String resultRequest="	"
    + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/osg/ussd/notification_manager/v1_0/local\"> \n" +
    "		<soapenv:Header> <tns:RequestSOAPHeader xmlns:tns=\"http://www.huawei.com.cn/schema/common/v2_1\"> \n" +
    "			<tns:spId>"+requestSoapHeader.getSpId()+"</tns:spId>\n" +
    "			<tns:spPassword>"+requestSoapHeader.getSpPassword()+"</tns:spPassword> \n" +
    "			<tns:serviceId>"+requestSoapHeader.getServiceId()+"</tns:serviceId> \n" +
    "			<tns:timeStamp>"+requestSoapHeader.getTimeStamp()+"</tns:timeStamp> \n" +
    "			</tns:RequestSOAPHeader> \n" +
    "		</soapenv:Header> 		\n" +
    "		<soapenv:Body> \n" +
    "			<loc:startUSSDNotification> \n" +
    "				<loc:reference> \n" +
    "				<endpoint>"+sdpSpProfile.getLocalEndPoint()+"</endpoint> \n" +
    "				<interfaceName>"+sdpSpProfile.getInterfaceName()+"</interfaceName> \n" +
    "				<correlator>"+sdpSpProfile.getCorrerator()+"</correlator> </loc:reference> \n" +
    "				<loc:ussdServiceActivationNumber>*"+sdpSpProfile.getCode()+"</loc:ussdServiceActivationNumber> \n" +
    "			</loc:startUSSDNotification> \n" +
    "		</soapenv:Body>\n" +
    "	</soapenv:Envelope>";

    return resultRequest;
    }
    
    public String buildStopUssdNotificationRequest(RequestSoapHeader requestSoapHeader,SdpSpProfile sdpSpProfile){
 
    String resultRequest=""+
"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/parlayx/sms/notification_manager/v2_3/local\">\n" +
"    <soapenv:Header>\n" +
"        <tns:RequestSOAPHeader xmlns:tns=\"http://www.huawei.com.cn/schema/common/v2_1\">\n" +
"            <tns:spId>"+requestSoapHeader.getSpId()+"</tns:spId>\n" +
"            <tns:serviceId>"+requestSoapHeader.getServiceId()+"</tns:serviceId>\n" +
"            <tns:timeStamp>"+requestSoapHeader.getTimeStamp()+"</tns:timeStamp>\n" +
"            <tns:spPassword>"+requestSoapHeader.getSpPassword()+"</tns:spPassword>\n" +
"        </tns:RequestSOAPHeader>\n" +
"    </soapenv:Header>\n" +
"    <soapenv:Body>\n" +
"        <loc:stopUSSDNotification>\n" +
"            <loc:correlator>"+sdpSpProfile.getCorrerator()+"</loc:correlator>\n" +
"        </loc:stopUSSDNotification>\n" +
"    </soapenv:Body>\n" +
"</soapenv:Envelope>";

    return resultRequest;
    }

    public String buildUssdSendRequest(RequestSoapHeader requestSoapHeader,SendUssdRequest sendUssdRequest){


    String resultRequest=""
    + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/parlayx/ussd/send/v1_0/local\"> \n" +
    "		<soapenv:Header> \n" +
    "			<tns:RequestSOAPHeader xmlns:tns=\"http://www.huawei.com.cn/schema/common/v2_1\"> \n" +
    "				<tns:spId>"+requestSoapHeader.getSpId()+"</tns:spId> \n" +
    "				<tns:spPassword>"+requestSoapHeader.getSpPassword()+"</tns:spPassword> \n" +
    "				<tns:serviceId>"+requestSoapHeader.getServiceId()+"</tns:serviceId> <tns:timeStamp>"+requestSoapHeader.getTimeStamp()+"</tns:timeStamp> \n" +
    "				<tns:OA>"+requestSoapHeader.getOA()+"</tns:OA>\n" +
    "				<tns:FA>"+requestSoapHeader.getFA()+"</tns:FA> \n" +
    "				<tns:linkid>"+requestSoapHeader.getLinkId()+"</tns:linkid> \n" +
    "			</tns:RequestSOAPHeader> \n" +
    "		</soapenv:Header> \n" +
    "		<soapenv:Body> \n" +
    "			<loc:sendUssd> \n" +
    "				<loc:msgType>"+sendUssdRequest.getMsgType()+"</loc:msgType> \n" +
    "				<loc:senderCB>"+sendUssdRequest.getSenderCB()+"</loc:senderCB>\n" +
    "				<loc:receiveCB>"+sendUssdRequest.getReceiveCB()+"</loc:receiveCB> \n" +
    "				<loc:ussdOpType>"+sendUssdRequest.getUssdOpType()+"</loc:ussdOpType> \n" +
    "				<loc:msIsdn>"+sendUssdRequest.getMsIsdn()+"</loc:msIsdn> \n" +
    "				<loc:serviceCode>"+sendUssdRequest.getServiceCode()+"</loc:serviceCode> \n" +
    "				<loc:codeScheme>"+sendUssdRequest.getCodeScheme()+"</loc:codeScheme> \n" +
    "				<loc:ussdString>"+sendUssdRequest.getUssdString()+"</loc:ussdString> \n" +
    "			</loc:sendUssd> \n" +
    "		</soapenv:Body> \n" +
    "	</soapenv:Envelope>";

    return resultRequest;
    }

    private String getNotifyUssdReceptionResponse(){
    return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/parlayx/ussd/notification/v1_0/local\"> \n" +
    "		<soapenv:Header/> \n" +
    "		<soapenv:Body> \n" +
    "			<loc:notifyUssdReceptionResponse> \n" +
    "				<loc:result>0</loc:result> \n" +
    "			</loc:notifyUssdReceptionResponse> \n" +
    "		</soapenv:Body> \n" +
    "	</soapenv:Envelope>";
    }
    
    private String getErrorNotifyUssdReceptionResponse(){
    return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/parlayx/ussd/notification/v1_0/local\"> \n" +
    "		<soapenv:Header/> \n" +
    "		<soapenv:Body> \n" +
    "			<loc:notifyUssdReceptionResponse> \n" +
    "				<loc:result>SVC0002</loc:result> \n" +
    "			</loc:notifyUssdReceptionResponse> \n" +
    "		</soapenv:Body> \n" +
    "	</soapenv:Envelope>";
    }
    
    public String sendUssdRequest(){
        String outgoingUssdRequest=""
                + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/parlayx/ussd/send/v1_0/local\"> \n" +
"		<soapenv:Header> \n" +
"			<tns:RequestSOAPHeader xmlns:tns=\"http://www.huawei.com.cn/schema/common/v2_1\"> \n" +
"				<tns:spId>000201</tns:spId> \n" +
"				<tns:spPassword>e6434ef249df55c7a21a0b45758a39bb</tns:spPassword> \n" +
"				<tns:serviceId>35000001000029</tns:serviceId> <tns:timeStamp>20100731064245</tns:timeStamp> \n" +
"				<tns:OA>8613300000010</tns:OA>\n" +
"				<tns:FA>8613300000010</tns:FA> \n" +
"				<tns:linkid>12345678901111</tns:linkid> \n" +
"			</tns:RequestSOAPHeader> \n" +
"		</soapenv:Header> \n" +
"		<soapenv:Body> \n" +
"			<loc:sendUssd> \n" +
"				<loc:msgType>0</loc:msgType> \n" +
"				<loc:senderCB>306909975</loc:senderCB>\n" +
"				<loc:receiveCB/> \n" +
"				<loc:ussdOpType>1</loc:ussdOpType> \n" +
"				<loc:msIsdn>8633699991234</loc:msIsdn> \n" +
"				<loc:serviceCode>2929</loc:serviceCode> \n" +
"				<loc:codeScheme>68</loc:codeScheme> \n" +
"				<loc:ussdString>please select: Menuplease</loc:ussdString> \n" +
"			</loc:sendUssd> \n" +
"		</soapenv:Body> \n" +
"	</soapenv:Envelope>";
        
        return "";
                
    }
     


   
}


