/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Id;

/**
 *
 * @author ismaelnzamutuma
 */
public class UssdRouteKey implements Serializable{
    
    @Id
    private String shortCode;
    @Id
    private String ext;
    
    public UssdRouteKey (String shortcode,String ext)
    {
        this.ext=ext;
        this.shortCode=shortcode;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof UssdRouteKey))
            return false;
        if(obj==this)
            return true;
        
        return false;
        
        
        
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hashCode(this);
    }
    
    /**
     * @return the shortCode
     */
    public String getShortCode() {
        return shortCode;
    }
    
    /**
     * @param shortCode the shortCode to set
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
    
    /**
     * @return the ext
     */
    public String getExt() {
        return ext;
    }
    
    /**
     * @param ext the ext to set
     */
    public void setExt(String ext) {
        this.ext = ext;
    }
    
    
}
