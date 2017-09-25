/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entities.Product;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel Losvik
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable{
    
    private Product[] products = {
        new Product("Bibel", "null", "null", 0.0, null, null),
        new Product("Bibel 2", "null", "null", 0.0, null, null),
        new Product("Bibel 3", "null", "null", 0.0, null, null),
        new Product("Bibel 4", "null", "null", 0.0, null, null),
    };
    
    //@EJB
    //private com.forest.ejb.UserBean ejbFacade;
    private Product product;
    private String name;
    private String features;
    
    @Inject
    //CustomerController customerController;


    /**
     * Creates a new instance of ProductBean
     */
    public ProductController() {
    }
    
    public Product[] getProducts() {
        return products;
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
    
    
    
}
