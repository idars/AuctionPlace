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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An entity representing a Product.
 * 
 * A product is a valuable item that can be auctioned. Each product is assigned 
 * an auction, which means that there is no need to create a new auction when 
 * creating a Product.
 * 
 * The owning Customer can auction a product, and other Customers can bid on it 
 * once it's published. Each product has a deadline (which can be postponed), 
 * and once the deadline has been reached, the Customer with the highest bid 
 * wins the acution.
 * 
 * Note that the rating has no relation to the rating of a Customer.
 */
@Entity
@XmlRootElement
public class Product implements Serializable {

    private static final long serialVersionUID = 4L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * The current status of the product. The values follow the different 
     * lifecycle phases that an auction can have:
     * 
     * <ul>
     * <li>UNPUBLISHED: Auction is not active</li>
     * <li>PUBLISHED: Auction is active</li>
     * <li>SOLD: Auction is finished</li>
     * </ul>
     */
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

    /**
     * Constructs an empty Product object.
     */
    public Product() {
        this.name = "";
        this.picture = "";
        this.features = "";
        this.rating = 0;
        this.whenBiddingCloses = null;
        this.status = null;
        this.owner = null;
    }

    /**
     * Constructs a Product object with the specified information.
     * 
     * @param name the name of the product
     * @param picture an absolute URL of an HTML-supported image
     * @param features a comment describing the product's features
     * @param rating the rating of the product
     * @param whenBiddingCloses the deadline indicating when the auction ends
     * @param status the status of the product
     * @param owner the Customer auctioning this product
     */
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
