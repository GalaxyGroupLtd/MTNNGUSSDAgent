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
public class NotifyUssdReceptionRequest {

    private  NotifySOAPHeader header;
    private NotifyUssdReception body;
    
    
    /**
     * @return the header
     */
    public NotifySOAPHeader getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(NotifySOAPHeader header) {
        this.header = header;
    }

    /**
     * @return the body
     */
    public NotifyUssdReception getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(NotifyUssdReception body) {
        this.body = body;
    }
}
