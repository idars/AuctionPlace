package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * An entity representing a bid. 
 * 
 * A bid can be placed on products with a status of PUBLISHED. It may be placed
 * in two ways:
 * 
 * <ul>
 * <li>As a one-time bid, where only amount is specified.</li>
 * <li>As a recurrent bid (so-called automatic bidding), where maxAmount is also
 * specified.</li>
 * </ul>
 * 
 * When placing a recurrent bid,  Bids with increasing amount will be placed 
 * until maxAmount is reached or the auction is won.
 */
@Entity
public class Bid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Double amount;
    private Double maxAmount;
    private Boolean automaticBidding;
    
    @ManyToOne
    @JoinColumn(name = "BIDDER_ID", referencedColumnName = "ID")
    private Customer bidder;

    /**
     * Constructs an empty Bid object.
     */
    public Bid() {
        this.amount = null;
        this.maxAmount = null;
        this.automaticBidding = null;
        this.bidder = null;
    }

    /**
     * Constructs a Bid object with the specified information.
     * 
     * @param amount the value to bid
     * @param maxAmount the maximum value limit, if recurring
     * @param automaticBidding true if the bid should be recurring, false otherwise
     * @param bidder the Customer to place the bid
     */
    public Bid(Double amount, Double maxAmount, Boolean automaticBidding, Customer bidder) {
        this.amount = amount;
        this.maxAmount = maxAmount;
        this.automaticBidding = automaticBidding;
        this.bidder = bidder;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Boolean isAutomaticBidding() {
        return automaticBidding;
    }

    public void setAutomaticBidding(Boolean automaticBidding) {
        this.automaticBidding = automaticBidding;
    }

    public Customer getBidder() {
        return bidder;
    }

    public void setBidder(Customer bidder) {
        this.bidder = bidder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bid[ id=" + id + " ]";
    }
    
}
