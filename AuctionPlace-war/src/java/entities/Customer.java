/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
 * 
 */
@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email")
})
public class Customer implements Serializable {

    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Double rating;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner") // maps to the "owner" member field of Product
    private List<Product> catalog;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidder")
    private List<Bid> bids;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Feedback> feedbacks;
    
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    @XmlTransient
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to be set
     */
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
