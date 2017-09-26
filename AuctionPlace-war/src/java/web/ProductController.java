/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entities.Product;
import entities.Customer;
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
    private Product product;
    private String name;
    private String features;
    
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
    
    public String addProduct(Customer customer) {
        this.ejbFacade.addProduct(new Product(
                this.getName(),
                null,
                null,
                null,
                null,
                null,
                customer
        ));
        customerController.updateCustomer();
        this.updateProducts();
        return "catalogue";
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
    
    
    
}
