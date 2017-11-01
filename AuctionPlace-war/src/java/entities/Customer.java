package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 * An entity representing a Customer.
 * 
 * A Customer can be both a seller and a bidder. As such, this class can 
 * perform both selling- and bidding-specific operations. Each Customer is able
 * to auction products, display them in their catalog, and to bid on other
 * auctions.
 */
@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email")
})
public class Customer implements Serializable {

    private static final long serialVersionUID = 2L;
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(nullable = false)
    @Id
    private String email;
    @Column(nullable = false)
    private String password;
    private String phone;
    private Double rating;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner") // maps to the "owner" member field of Product
    @XmlTransient
    private List<Product> catalog;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidder")
    @XmlTransient
    private List<Bid> bids;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @XmlTransient
    private List<Feedback> feedbacks;
    
    /**
     * Constructs an empty Customer object.
     */
    public Customer() {
        name = "";
        email = "";
        password = "";
        phone = "";
        rating = 0.0;
        catalog = new ArrayList<>();
        bids = new ArrayList<>();
        feedbacks = new ArrayList<>();
    }
    
    /**
     * Constructs a Customer object with the specified information.
     * 
     * Each parameter must consist of at least 2 characters, unless otherwise 
     * stated.
     * 
     * @param name the name of the Customer
     * @param email the mail address, fulfilling the regex 
     * @param password the hash result of the Customer's password
     * @param phone the phone number, including country codes
     * @param rating the Customers rating, which can be a value between 1.0 and 
     * 5.0
     */
    public Customer(
            String name, 
            String email, 
            String password, 
            String phone,
            Double rating) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.rating = rating;
        this.catalog = new ArrayList<>();
        this.bids = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Product> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<Product> catalog) {
        this.catalog = catalog;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Customer[ id=" + id + " ]";
    }
    
}
