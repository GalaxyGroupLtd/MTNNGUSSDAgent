/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.utilities;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import static java.lang.System.out;
import java.security.KeyStore;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.cxf.transport.https.CertificateHostnameVerifier;

/**
 *
 * @author manzi
 */
public class SSLProcedures {
    private static KeyStore loadStore(String trustStoreFile, String password) throws Exception {
        KeyStore store = KeyStore.getInstance("JKS");
        
        store.load(new FileInputStream(trustStoreFile), password.toCharArray());
        return store;
    }
    
    private static Client buildClientProd(){
        try{
            ClientBuilder builder = ClientBuilder.newBuilder();
            
            
            //production
          //  KeyStore trustStore = loadStore("/home/glassfish/glassfish/domains/domain1/config/mtnecw/oltranzECWProd.jks","kigali2day!");
            
            //builder.trustStore(trustStore);
            builder.hostnameVerifier(CertificateHostnameVerifier.ALLOW_ALL);
            
            
            //Production
            //KeyStore keyStore = loadStore("/home/glassfish/glassfish/domains/domain1/config/mtnecw/oltranzECWProd.jks","kigali2day!");
            
            
            //builder.keyStore(keyStore,"mtnEcw");
            out.print("MTN AGENT-TEST USED JKS FILE: "+"/home/glassfish/glassfish/domains/domain1/config/mtnecw/oltranzECWProd.jks");
            Client client = builder.build();
            return client;
        }catch(Exception Ex){
            out.print("MTN AGENT-TEST Building client error: "+Ex.getMessage());
            return null;
        }
    }
    
    private static Client buildClientTest(){
        try{
            ClientBuilder builder = ClientBuilder.newBuilder();
            
            //test
            KeyStore trustStore = loadStore("/home/glassfish/glassfish/domains/domain1/config/mtnecwcertificates/mtntestkeystore.jks","kigali2day!");
            
            
            builder.trustStore(trustStore);
            builder.hostnameVerifier(CertificateHostnameVerifier.ALLOW_ALL);
            
            //test
            KeyStore keyStore = loadStore("/home/glassfish/glassfish/domains/domain1/config/mtnecwcertificates/mtntestkeystore.jks","kigali2day!");
             out.print("MTN AGENT-TEST USED JKS FILE: "+"/home/glassfish/glassfish/domains/domain1/config/mtnecwcertificates/mtntestkeystore.jks");
            
            builder.keyStore(keyStore,"mtnecw");
            
            Client client = builder.build();
            return client;
        }catch(Exception Ex){
            return null;
        }
    }
    
    public static Response sendGETRequest(String url,String requestType){
        try
        {
            //Enable this for debugging on local machine/netbean
            //Client client =ClientBuilder.newClient();
            
            //Enable this before deploying to server
            Client client=null;
            
            if(requestType.equals("test"))
                client = buildClientTest();
            if(requestType.equals("prod"))
                client = buildClientProd();
            
            if(client==null)
                return null;
            out.print("URL----------------------------------: "+url);
            WebTarget target =client.target(url);
            
            
            
            Response response = target.request().get();
            
            return response;
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
            return null;
        }
        
        
    }
    
    public static Response postRequest(String url,String requestStr,String MediaType, String method,String requestType){
        try
        {
            
            Client client=null;
            
            out.print("MTN AGENT-TEST : URL----------------------------------\n: "+url);
            
            if(requestType.equals("test")){
                client = buildClientTest();
                out.print("MTN AGENT-TEST : URL test----------------------------------: "+url);
            }
            if(requestType.equals("prod")){
                client = buildClientProd();
                out.print("MTN AGENT-TEST : URL production----------------------------------: "+url);
            }
            
            if(client==null)
                return null;
            out.print("MTN AGENT-TEST : Client----------------------------------: "+client);
            
            WebTarget target =client.target(url);
            
            Response response = target.request().post(Entity.entity(requestStr, MediaType));
            
            return response;
        }
        catch(Exception e)
        {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            out.printf("MTN AGENT-TEST - Error request send :"+ e.getMessage() + "TRACE: "+ message);
            // e.printStackTrace(Systecd m.out);
            return null;
        }
        
        
    }
    
    public static Response postRequest(String url,String requestStr,MultivaluedMap<String,Object> headers,String MediaType, String method,String requestType){
        try
        {
            
            Client client=null;
            
            out.print("MTN AGENT-TEST : URL----------------------------------\n: "+url);
            
            if(requestType.equals("test")){
                client = buildClientTest();
                out.print("MTN AGENT-TEST : URL test----------------------------------: "+url);
            }
            if(requestType.equals("prod")){
                client = buildClientProd();
                out.print("MTN AGENT-TEST : URL production----------------------------------: "+url);
            }
            
            if(client==null)
                return null;
            out.print("MTN AGENT-TEST : Client----------------------------------: "+client);
            out.print("MTN AGEBT-TEST : Body="+requestStr);
            
            WebTarget target =client.target(url);
            
            Response response;
            if(headers != null)
                response = target.request().headers(headers).post(Entity.entity(requestStr, MediaType));
            else
                response = target.request().post(Entity.entity(requestStr, MediaType));
            
            
            return response;
        }
        catch(Exception e)
        {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            out.printf("MTN AGENT-TEST - Error request send :"+ e.getMessage() + "TRACE: "+ message);
            // e.printStackTrace(Systecd m.out);
            return null;
        }
        
        
    }
    
    
}
