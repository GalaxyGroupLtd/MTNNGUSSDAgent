/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package atn.mtnng.ussdagent.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="ussd_routes")
@IdClass(UssdRouteKey.class)
public class UssdRoute  implements Serializable{
    @Id
    @Column(name = "short_code", length = 50)
    private String shortCode;
    @Id
    @Column(name = "ext", length = 50)
    private String ext;
    
    @Column(name = "url", length = 255)
    private String url;
    
    @Column(name = "description", length = 255)
    private String description;
    
    
    public String getShortCode() {
        return shortCode;
    }
    
    /**
     * @param shortCode the shortCode to set
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
    
    public String getUrl() {
        return url;
    }
    
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return the ext
     */
    public String getExt() {
        return ext;
    }
    
    /**
     * @param extens the ext to set
     */
    public void setExt(String ext) {
        this.ext = ext;
    }
    
    
}
