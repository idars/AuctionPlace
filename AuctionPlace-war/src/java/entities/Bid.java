/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ben
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
    
    @JoinColumn(name = "BIDDER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Customer bidder;

    public Bid() {
        this.id = null;
        this.amount = null;
        this.maxAmount = null;
        this.automaticBidding = null;
        this.bidder = null;
    }

    public Bid(Long id, Double amount, Double maxAmount, Boolean automaticBidding, Customer bidder) {
        this.id = id;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
