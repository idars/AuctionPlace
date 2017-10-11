package ejb;

import entities.Bid;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daniel Losvik
 */
@Stateless
public class BidBean extends AbstractFacade<Bid> {
    
    @PersistenceContext(unitName="AuctionPlace-warPU")
    private EntityManager em;
    
    public BidBean() {
        super(Bid.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Update the bid inn the database
     * @param b the bid to update
     */
    public void updateBid(Bid b) {
        this.edit(b);
    }
}
