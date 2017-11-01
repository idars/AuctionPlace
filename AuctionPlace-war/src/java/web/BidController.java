package web;

import entities.Bid;
import java.io.Serializable;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 */
@DeclareRoles({"user"})
@Named(value = "bidController")
@SessionScoped
public class BidController implements Serializable {
    
    private Bid bid;
    @EJB
    private ejb.BidBean ejbFacade;
    @Inject
    private CustomerController customerController;
    
    private Double amount;
    private Double maxAmount;
    private Boolean automaticBidding;
    
    private String errorMessage;
    
    @Inject
    ProductController productController;

    /**
     * Creates a new instance of BidController
     */
    public BidController() {
        this.automaticBidding = false;
    }
    /*
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
    */
    /**
     * Send in a new bid on the product if the bid is higher then current bid
     * @return the page to navigate to
     */
    @RolesAllowed("user")
    public String sendBid() {
        Double currentAmount = productController.getProduct().getCurrentBid().getAmount();
        Double currentMax = productController.getProduct().getCurrentBid().getMaxAmount();
        
        // check max amount for automatic bids
        if(this.getAutomaticBidding() == true && (
                this.getMaxAmount() == null 
                || this.getAmount() == null
                || this.getMaxAmount() <= this.getAmount())) {
            this.setErrorMessage("Max Amount must be greater then amount");
            return "place_bid";
        }
        
        // check if amount is greater then current bid
        // else error message
        if (this.getAmount() != null && this.getAmount() >  currentAmount) {
            
            // check if automatic bidding is enabled on current bid
            // and if max is greater then new bid
            if(productController.getProduct().getCurrentBid().isAutomaticBidding()
                    && this.getAmount() < currentMax && this.getAutomaticBidding() == false) {
                
                    // increase current bid by 1
                    if(currentAmount + 1 < currentMax) {
                        productController.getProduct().getCurrentBid().setAmount(this.getAmount() + 1);
                    }
                    // increase current bid to max
                    else {
                        productController.getProduct().getCurrentBid().setAmount(currentMax);
                    }
                
            }
            else {
                // check if both have automatic bidding enabled
                // and who has greatest max amount
                if(this.getAutomaticBidding() == true
                        && productController.getProduct().getCurrentBid().isAutomaticBidding()
                        && this.getAmount() < currentMax) {
                    
                    // increanse current bid to the new bids max
                    if(this.getAutomaticBidding() == true && this.getMaxAmount() <= currentMax) {
                        productController.getProduct().getCurrentBid().setAmount(this.getMaxAmount());
                    }
                    
                    // send new bid with amount equal last bids max
                    else if(this.getAutomaticBidding() == true && this.getMaxAmount() > currentMax) {
                        productController.getProduct().getCurrentBid().setAmount(currentMax);
                        productController.getProduct().getCurrentBid().setAutomaticBidding(this.getAutomaticBidding());
                        productController.getProduct().getCurrentBid().setMaxAmount(this.getMaxAmount());
                        productController.getProduct().getCurrentBid().setBidder(customerController.getCustomer());
                    }
                }
               
                // send new bid
                else {
                    productController.getProduct().getCurrentBid().setAmount(this.getAmount());
                    productController.getProduct().getCurrentBid().setAutomaticBidding(this.getAutomaticBidding());
                    productController.getProduct().getCurrentBid().setMaxAmount(this.getMaxAmount());
                    productController.getProduct().getCurrentBid().setBidder(customerController.getCustomer());
                }
            
            }
            this.ejbFacade.updateBid(productController.getProduct().getCurrentBid());
            this.setErrorMessage(null);
            return "bid_receipt";
        }
        else {
            this.setErrorMessage("Amount must be greater then current bid");
            return "place_bid";
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
    
    /**
     * Turn on or off automatic bidding
     */
    public void toggleAutomaticBiddding() {
        this.setAutomaticBidding(!this.getAutomaticBidding());
    }
    
    /**
     * Clear all the bid input fields
     */
    public void clearInputFields() {
        this.setAmount(null);
        this.setAutomaticBidding(false);
        this.setMaxAmount(null);
        this.setErrorMessage(null);
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
