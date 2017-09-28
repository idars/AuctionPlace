/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entities.Bid;
import entities.Feedback;
import entities.Product;
import entities.Product.Status;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Daniel Losvik
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable{
    
    @EJB
    private ejb.ProductBean ejbFacade;
    private Product product;
    private String name;
    private String features;
    private String picture;
    private int ratings;
    private double startingAmount;
    private int timeLeftHours;
    private int timeLeftMinutes;
    private String feedbackMessage;
    
    private String successMessage;
    
    @Inject
    CustomerController customerController;
    
    List<Product> products;


    /**
     * Creates a new instance of ProductBean
     */
    public ProductController() {}
    
    @PostConstruct
    public void updateProducts() {
        this.setProducts(this.ejbFacade.getProducts());
    }
    
    public String addProduct() {
        
        // check Product Image
        String picture = "http://www.novelupdates.com/img/noimagefound.jpg";
        if(this.getPicture() != null && !(this.getPicture().equals(""))) {
            picture = this.getPicture();
        }
        
        // check When bidding closes
        Timestamp time = new Timestamp(System.currentTimeMillis()
                + this.getTimeLeftHours() * 3600000 
                + this.getTimeLeftMinutes() * 60000);
        
        // Create starting bid and new Product
        Bid startingBid = new Bid(this.getStartingAmount(), 0.0, false, customerController.getCustomer());
        Product newProduct = (new Product(
                this.getName(),
                picture,
                this.getFeatures(),
                0,
                time,
                Status.UNPUBLISHED,
                customerController.getCustomer()
        ));
        newProduct.setCurrentBid(startingBid);
        this.ejbFacade.addProduct(newProduct);
        customerController.getCustomer().getCatalog().add(newProduct);
        this.updateProducts();
        return "catalogue";
    }
    
    public void publishProduct(Product p) {
        if(p.getStatus() == Status.UNPUBLISHED) {
            p.setStatus(Status.PUBLISHED);
        }
        else if (p.getStatus() == Status.PUBLISHED) {
            p.setStatus(Status.UNPUBLISHED);
        }
        this.ejbFacade.updateProduct(p);
        this.updateProducts();
    }
    
    public boolean isPublished(Product p) {
        return p.getStatus() == Status.PUBLISHED;
    }
    
    public boolean isUnPublished(Product p) {
        return p.getStatus() == Status.UNPUBLISHED;
    }
    
    public boolean isSold(Product p) {
        return p.getStatus() == Status.SOLD;
    }
    
    public String formatTimeLeft(Product p) {
        Long millis = p.getWhenBiddingCloses().getTime() - System.currentTimeMillis();
        
        String timeLeft = String.format("%02d:%02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(millis),
        TimeUnit.MILLISECONDS.toHours(millis)   % TimeUnit.DAYS.toHours(1),
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        
        if(millis < 0) {
            if(this.isSold(p) == false) {
                p.setStatus(Status.SOLD);
                this.ejbFacade.updateProduct(p);
                this.updateProducts();
                
            }
            return "SOLD";
        }
        else {
            return timeLeft;
        }
    }
    
    public void sendFeedback() {
        Feedback f = new Feedback(this.getFeedbackMessage(), customerController.getCustomer());
        this.getProduct().setRating(this.getRatings());
        this.getProduct().setFeedback(f);
        this.ejbFacade.updateProduct(this.getProduct());
        this.updateProducts();
    }
    
    public String seeFeedback(Product p) {
        this.setProduct(p);
        return "product_details";
    }
    
    public void editProduct(Product p) {
        this.setProduct(p);
        this.setName(p.getName());
        this.setPicture(p.getPicture());
        this.setFeatures(p.getFeatures());
        this.setSuccessMessage(null);
    }
    
    public String saveChanges() {
        this.getProduct().setName(this.getName());
        this.getProduct().setFeatures(this.getFeatures());
        this.getProduct().setPicture(this.getPicture());
        this.ejbFacade.updateProduct(this.getProduct());
        this.updateProducts();
        this.setSuccessMessage("Updates was succsessfull");
        return "product_change";
    }
    
    

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(double startingAmount) {
        this.startingAmount = startingAmount;
    }

    public int getTimeLeftHours() {
        return timeLeftHours;
    }

    public void setTimeLeftHours(int timeLeftHours) {
        this.timeLeftHours = timeLeftHours;
    }

    public int getTimeLeftMinutes() {
        return timeLeftMinutes;
    }

    public void setTimeLeftMinutes(int timeLeftMinutes) {
        this.timeLeftMinutes = timeLeftMinutes;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    
    
    
    
}
