/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;


//import javax.enterprise.context.Dependent;

import DB.MockupDB;

import entities.Product;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
//import javax.enterprise.context.Dependent;
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
@Named(value = "productBean")
//@Dependent
@SessionScoped
public class ProductBean implements Serializable{
    
    private Product[] products = {
        new Product("Bibel", "null", "null", 0.0, null, null, null),
        new Product("Bibel 2", "null", "null", 0.0, null, null, null),
        new Product("Bibel 3", "null", "null", 0.0, null, null, null),
        new Product("Bibel 4", "null", "null", 0.0, null, null, null),
    };
    
    //@EJB
    //private com.forest.ejb.UserBean ejbFacade;
    private Product product;
    private String name;
    
    @Inject
    //CustomerController customerController;


    /**
     * Creates a new instance of ProductBean
     */
    public ProductBean() {
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
    
    
    
}
