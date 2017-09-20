/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import DB.MockupDB;

import entities.Customer;
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
@Named(value = "customerBean")
//@Dependent
@SessionScoped
public class CustomerBean implements Serializable {
    
    private Customer customer;
    //@EJB
    //private com.forest.ejb.UserBean ejbFacade;
    private String name;
    private String email;
    private String password;
    private String phone;
    @Inject
    //CustomerController customerController;

    /**
     * Creates a new instance of CustomerBean
     */
    public CustomerBean() {
    }
    
    /**
     * Register a new user
     * -> must check if email already exist
     */
    public String register() {
        MockupDB.addNewCustomer(new Customer(
                this.getName(),
                this.getEmail(),
                this.getPassword(),
                this.getPhone()
        ));
        return "login";
    }
    
    /**
     * Login the customer if the given credentials are valid
     */
    public void login() {
        Customer customer = MockupDB.getCustomer(this.getEmail(), this.getPassword());
        if(customer != null) {
            setCustomer(customer);
        }
    }
    
    public boolean isLogged() {
        return (getCustomer() != null);
    }
    
    public void testLogin() {
        setCustomer(new Customer(
                "Test User",
                "test@gmail.com",
                "12345678",
                "12345678"
        ));
    }
    
    
    

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
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
    
}
