/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import entities.Customer;
import entities.Product;


/**
 *
 * @author Ben
 */
public class SetBidStatus {
    
    
    private Integer code;
    private Customer customer;
    private Product product;
    
    public SetBidStatus() {
        this.code=0;
        this.customer = null;
        this.product = null;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    
    
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }
    
    public Product getProduct() {
        return this.product;
    }
    
    public Integer getCode() {
        return this.code;
    }
    
 
    @Override
    public String toString() {
         if (code == 200) 
             return this.customer.getName() + "’s bid has been successfully placed for " + this.product.getName();
        else 
             return "The bid for " + this.product.getName() + " has not been placed for " + this.product.getName();
    }
}
