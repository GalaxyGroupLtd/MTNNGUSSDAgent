/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.utilities;


import atn.mtnng.ussdagent.models.NotifySOAPHeader;
import atn.mtnng.ussdagent.models.NotifyUssdReception;
import atn.mtnng.ussdagent.models.NotifyUssdReceptionRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import static java.lang.System.out;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

/**
 *
 * @author manzi
 */
public class SoapXmlRequest2tBeans {
    /**
     * this method is used to transform the Mobile Money response into
     * MmResponse object
     *
     * @param xmlRequest
     * @return
     */
    
    private static String getStringFromDoc(org.w3c.dom.Document doc)    {
        DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
        LSSerializer lsSerializer = domImplementation.createLSSerializer();
        return lsSerializer.writeToString(doc);
    }
    
    
    public static NotifyUssdReceptionRequest extractNotifyUssdReceptionRequest(String inputString) {
        try {
            InputStream is = new ByteArrayInputStream( inputString.getBytes() );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            String source= getStringFromDoc(doc);
            source= source.replaceAll("<[a-zA-Z0-9]+:","<");
            source= source.replaceAll("</[a-zA-Z0-9]+:","</");
            
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource ist = new InputSource();
            ist.setCharacterStream(new StringReader(source));
            doc = db.parse(ist);
            
            
            NotifyUssdReceptionRequest notifyUssdReceptionRequest=new NotifyUssdReceptionRequest();
            
            NodeList nodes = doc.getElementsByTagName("NotifySOAPHeader");
            NotifySOAPHeader notifySOAPHeader;
            if(nodes!=null){
                String NotifySOAPHeaderXML=CommonLibrary.nodeToString(nodes.item(0));
                notifySOAPHeader=(NotifySOAPHeader)CommonLibrary.unmarshalling(NotifySOAPHeaderXML, NotifySOAPHeader.class);
                notifyUssdReceptionRequest.setHeader(notifySOAPHeader);
            }

            nodes = doc.getElementsByTagName("notifyUssdReception");
            NotifyUssdReception notifyUssdReception=null;
            if(nodes!=null){
                String notifyUssdReceptionXML=CommonLibrary.nodeToString(nodes.item(0));
                notifyUssdReception=(NotifyUssdReception)CommonLibrary.unmarshalling(notifyUssdReceptionXML, NotifyUssdReception.class);
                notifyUssdReceptionRequest.setBody(notifyUssdReception);
            }
        
        
        return notifyUssdReceptionRequest;
        
        } catch (Exception ex) {
            
            return null;
            
        }
    }
    
}
