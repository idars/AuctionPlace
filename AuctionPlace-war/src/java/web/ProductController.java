/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entities.Bid;
import entities.Product;
import entities.Product.Status;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Daniel Losvik
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable{
    
    @EJB
    private ejb.ProductBean ejbFacade;
    @EJB
    private ejb.BidBean bidejbFacade;
    private Product product;
    private String name;
    private String features;
    private String picture;
    private double startingAmount;
    private double bidAmount;
    private String bidErrorMessage;
    
    @Inject
    CustomerController customerController;
    
    List<Product> products;


    /**
     * Creates a new instance of ProductBean
     */
    public ProductController() {
    }
    
    @PostConstruct
    public void updateProducts() {
        this.setProducts(this.ejbFacade.getProducts());
    }
    
    public String addProduct() {
        String picture = "http://www.novelupdates.com/img/noimagefound.jpg";
        if(this.getPicture() != null && !(this.getPicture().equals(""))) {
            picture = this.getPicture();
        }
        Bid startingBid = new Bid(this.getStartingAmount(), 0.0, false, customerController.getCustomer());
        Product newProduct = (new Product(
                this.getName(),
                picture,
                null,
                null,
                null,
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
    
    public Boolean isPublished(Product p) {
        return p.getStatus() == Status.PUBLISHED;
    }
    
    public String sendBid() {
        if(this.getBidAmount() >  this.getProduct().getCurrentBid().getAmount()) {
            this.getProduct().getCurrentBid().setAmount(this.getBidAmount());
            this.getProduct().getCurrentBid().setBidder(customerController.getCustomer());
            this.bidejbFacade.updateBid(this.getProduct().getCurrentBid());
            this.setBidErrorMessage(null);
            return "bid_receipt";
        }
        else {
            this.setBidErrorMessage("Amount must be greater then current bid");
            return "place_bid";
        }
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

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getBidErrorMessage() {
        return bidErrorMessage;
    }

    public void setBidErrorMessage(String bidErrorMessage) {
        this.bidErrorMessage = bidErrorMessage;
    }

    
    
    
    
}
