/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.bizlogic.entitiesFacades;

import atn.mtnng.ussdagent.entities.SdpSpProfile;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author manzi
 */
@Stateless
public class SdpSpProfileFacade extends AbstractFacade<SdpSpProfile> {

    @PersistenceContext(unitName = "MTNNGUSSDAgentPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SdpSpProfileFacade() {
        super(SdpSpProfile.class);
    }
    
}
