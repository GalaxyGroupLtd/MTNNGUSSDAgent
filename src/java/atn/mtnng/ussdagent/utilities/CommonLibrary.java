/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.utilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import static java.lang.System.out;
import java.security.MessageDigest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.transport.https.CertificateHostnameVerifier;
import org.w3c.dom.Node;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.spi.ConnectorProvider;

/**
 *
 * @author manzi
 */
public class CommonLibrary {
    static public String md5hashing(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            return myHash;
        }catch(Exception ex){
            return null;
        }
    }
    
    static public Object unmarshalling(InputStream inputStream, Class className) {
        Object object=null;
        try{
            String inputXML=inputStream2String(inputStream);
            XMLInputFactory xmlFactory = XMLInputFactory.newFactory();
            StreamSource streamSource = new StreamSource(new StringReader(inputXML));
            XMLStreamReader streamReader= xmlFactory.createXMLStreamReader(streamSource);
            
            JAXBContext jc = JAXBContext.newInstance(className);
            Unmarshaller unMarshaller = jc.createUnmarshaller();
            object= unMarshaller.unmarshal(streamReader);
            
            inputStream.close();
            return object;
            
        }catch(Exception ex){
            return object;
        }
        finally{
            try{
                inputStream.close();
                
            }
            catch(IOException ex){
                String message="ERROR ON INPUT STREAM RECEPTION OF CENTER REQUEST: MESSAGE: "+ex.getMessage()+" | TRACE :";
                StringWriter errors = new StringWriter();
                ex.printStackTrace(new PrintWriter(errors));
                message+=errors.toString();
                out.print(message);
            }
        }
    }
    
    static public Object unmarshalling(String inputXML, Class className) {
        Object object=null;
        try{
            // String inputXML=inputStream2String(inputStream);
            XMLInputFactory xmlFactory = XMLInputFactory.newFactory();
            StreamSource streamSource = new StreamSource(new StringReader(inputXML));
            XMLStreamReader streamReader= xmlFactory.createXMLStreamReader(streamSource);
            
            JAXBContext jc = JAXBContext.newInstance(className);
            Unmarshaller unMarshaller = jc.createUnmarshaller();
            object= unMarshaller.unmarshal(streamReader);
            
            
            return object;
            
        }catch(Exception ex){
            return object;
            
        }
    }
    
    static public Object unmarshalling(String inputXML, Class className,String MediaType ) {
        Object object=null;
        
        try{
            if(MediaType.equalsIgnoreCase("xml")){
                // String inputXML=inputStream2String(inputStream);
                XMLInputFactory xmlFactory = XMLInputFactory.newFactory();
                StreamSource streamSource = new StreamSource(new StringReader(inputXML));
                XMLStreamReader streamReader= xmlFactory.createXMLStreamReader(streamSource);
                
                JAXBContext jc = JAXBContext.newInstance(className);
                Unmarshaller unMarshaller = jc.createUnmarshaller();
                object= unMarshaller.unmarshal(streamReader);
                
            }
            if(MediaType.equalsIgnoreCase("json")){
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                
                object= mapper.readValue(inputXML,className);
            }
            return object;
            
        }catch(Exception ex){
            return object;
            
        }
    }
    
    static public String marshalling(Object object,String MediaType){
        
        try{
            StringWriter writer = new StringWriter();
            if(MediaType.equalsIgnoreCase("json")){
                ObjectMapper mapper= new ObjectMapper();
                mapper.writeValue(writer, object);
            }
            
            if(MediaType.equals("xml")){
                
                JAXBContext ctx = JAXBContext.newInstance(object.getClass());
                Marshaller msh = ctx.createMarshaller();
                msh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                msh.marshal(object, writer);
            }
            
            String result= writer.toString();
            return result;
            
        }catch(Exception ex){
            return null;
        }
        
    }
    
    static public String marshalling(Object object){
        
        try{
            JAXBContext ctx = JAXBContext.newInstance(object.getClass());
            Marshaller msh = ctx.createMarshaller();
            msh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            msh.marshal(object, writer);
            return writer.toString();
            
        }catch(Exception ex){
            return null;
        }
        
    }
    
    static public String inputStream2String(InputStream stream ){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(stream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            
        } catch (Exception e) {
            out.print("InputStream to String: Exception "+e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    out.print("InputStream to String: Exception "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
        
    }
    
    /*
    
    */
    
    public static Response sendRESTRequest(String url,String requestStr, String MediaType, String method)
    {
        try
        {
            Response response=null;
            
                       Client client = IgnoreSSLClient.ignoreSSLClient();
            
            WebTarget target =client.target(url);
            switch (method){
                case "POST":
                    response = target.request().post(Entity.entity(requestStr, MediaType));
                    
                    break;
                    
                case "GET":
                    response = target.request().get();
                    break;
                    
            }
            
            return response;
        }
        catch(Exception e)
        {
           StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            Log.trace(CommonLibrary.class,  e.getMessage() + "\nTRACE: "+ message);
            return null;
        }
    }
    
    public static Response sendRESTRequest(String url,String requestStr,MultivaluedMap<String,Object> headers, String MediaType, String method)
    {
        try
        {
            Response response=null;
            Client client =ClientBuilder.newClient();
            WebTarget target =client.target(url);
            switch (method){
                case "POST":
                    if(headers != null)
                        response = target.request().headers(headers).post(Entity.entity(requestStr, MediaType));
                    else
                        response = target.request().post(Entity.entity(requestStr, MediaType));
                    break;
                    
                case "GET":
                    if(headers != null)
                        response = target.request().headers(headers).get();
                    else
                        response = target.request().get();
                    break;
                    
            }
            
            return response;
        }
        catch(Exception e)
        {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String message=errors.toString();
            Log.trace(CommonLibrary.class,  e.getMessage() + "\nTRACE: "+ message);
            return null;
        }
        
        
    }
    
    
    public static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }
    
   
    
}
