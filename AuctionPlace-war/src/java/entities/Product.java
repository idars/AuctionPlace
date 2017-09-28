/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ben
 */
@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 4L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    public enum Status {
        UNPUBLISHED, PUBLISHED, SOLD
    }
    
    private String name;
    private String picture;
    private String features;
    private int rating;
    private Status status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date whenBiddingCloses;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FEEDBACK_ID", referencedColumnName = "ID")
    private Feedback feedback;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    private Bid currentBid;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
    private Customer owner;

    public Product() {
        this.name = "";
        this.picture = "";
        this.features = "";
        this.rating = 0;
        this.whenBiddingCloses = null;
        this.status = null;
        this.owner = null;
    }

    public Product(String name, String picture, String features, int rating, Date whenBiddingCloses, Status status, Customer owner) {
        this.name = name;
        this.picture = picture;
        this.features = features;
        this.rating = rating;
        this.whenBiddingCloses = whenBiddingCloses;
        this.status = status;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getWhenBiddingCloses() {
        return whenBiddingCloses;
    }

    public void setWhenBiddingCloses(Date whenBiddingCloses) {
        this.whenBiddingCloses = whenBiddingCloses;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Bid getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Bid currentBid) {
        this.currentBid = currentBid;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Product[ id=" + id + " ]";
    }
    
}
