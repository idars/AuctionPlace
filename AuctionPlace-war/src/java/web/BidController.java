/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entities.Bid;
import entities.Customer;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Idar
 */
@Named(value = "bidController")
@Dependent
public class BidController {
    
    private Bid bid;
    @EJB
    private ejb.BidBean ejbFacade;
    @Inject
    private CustomerController customerController;
    
    private Double amount;
    private Double maxAmount;
    private Boolean automaticBidding;
    
    private String errorMessage;

    /**
     * Creates a new instance of BidController
     */
    public BidController() {}
    
    public boolean submit() {
        Customer customer = customerController.getCustomer();
        boolean success = ejbFacade.register(new Bid(
                amount, maxAmount, automaticBidding, customer
        ));
        if (success) {
            setErrorMessage(null);
            return true;
        } else {
            setErrorMessage("doh!");
            return false;
        }
    }
    
    public void validateBidValue(FacesContext context, UIComponent toValidate, Object value) {
        double input = (Double) value;
        // TODO get current bid from product, find its value
        // double currentValue = productController.getProduct().getCurrentBid().getAmount();
        double currentValue = 0.0;
        // TODO get minimum interval between bids, or use a default value specified by the application otherwise
        double minInterval = 0.0;
        if (input >= currentValue + minInterval) {
            FacesMessage message = new FacesMessage("Bid is lower than current bid or lower than the current bid plus the minimum interval");
            context.addMessage(toValidate.getClientId(context), message);
        }
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getAutomaticBidding() {
        return automaticBidding;
    }

    public void setAutomaticBidding(Boolean automaticBidding) {
        this.automaticBidding = automaticBidding;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
