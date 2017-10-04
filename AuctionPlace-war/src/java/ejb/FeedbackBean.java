/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Feedback;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author Daniel Losvik
 */
@Stateless
public class FeedbackBean extends AbstractFacade<Feedback> {

    @PersistenceContext(unitName = "AuctionPlace-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedbackBean() {
        super(Feedback.class);
    }
    
}
