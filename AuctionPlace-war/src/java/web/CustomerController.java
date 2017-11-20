package web;

import ejb.CustomerBean;
import entities.Customer;
import java.io.Serializable;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniel Losvik
 */
@DeclareRoles({"user"})
@Named(value = "customerController")
@SessionScoped
public class CustomerController implements Serializable {
    
    private Customer customer;
    @EJB
    private ejb.CustomerBean ejbFacade;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Double rating;
    
    private String errorMessage;
    private String successMessage;
    private String continuePage;
    
    @Inject
    ProductController productController;
    
    @Inject
    BidController bidController;

    /**
     * Creates a new instance of CustomerBean
     */
    public CustomerController() {
    }
    
    /**
     * Query the customer again after data is updated in the database
     * @throws Exception if encryption of password failed
     */
    public void updateCustomer() throws Exception {
        this.setCustomer(ejbFacade.loginCustomer(this.getCustomer().getEmail(), this.getCustomer().getPassword()));
    }
    
    /**
     * Register new customer or set error message if input is wrong
     * @return the side to navigate to
     * @throws Exception if encryption of password failed
     */
    public String register() throws Exception {
        boolean success = ejbFacade.RegisterNewCustomer(new Customer(
                this.getName(),
                this.getEmail(),
                this.getPassword(),
                this.getPhone(),
                this.getRating()
        ));
        if(!success) {
            setErrorMessage("Email " + this.getEmail() + " Already Exists");
            return "register";
        }
        else {
            setErrorMessage(null);
            return "login";
        }
    }
    
    /**
     * Login the customer if he exists and the password matches
     * @return the page to navigate to
     * @throws Exception if encryption of password failed
     */
    public String login() throws Exception {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        //request.login(this.getEmail() , this.getPassword());
        
        Customer customer = ejbFacade.loginCustomer(this.getEmail(), this.getPassword());
        if(customer != null) {
            setErrorMessage(null);
            setCustomer(customer);
            String page = this.getContinuePage();
            if(page != null) {
                this.setContinuePage(null);
                return page;
            }
            else {
                return "auction";
            }
        }
        else {
            setErrorMessage("Wrong email or password");
            return "login";
        }
    }
    
    /**
     * Logout the customer
     */
    public void logout() {
        setCustomer(null);
    }
    
    /**
     * Check if the customer is logged in
     * @return true if the customer is logged inn
     */
    public boolean isLogged() {
        return (getCustomer() != null);
    }
    
    /**
     * Check if the user is logged inn before navigating
     * @param page the page to navigate to
     * @return the page to navigate to
     */
    @RolesAllowed("user")
    public String navigateIfLogged(String page) {
        if(this.isLogged()) {
            bidController.clearInputFields();
            this.setErrorMessage(null);
            return page;
        }
        else {
            this.setContinuePage(page);
            this.setErrorMessage("Please login to continue");
            return "login";
        }
    }
    
    /**
     * Clear all customer input fields
     */
    public void clearInputFields() {
        this.setName(this.getCustomer().getName());
        this.setPhone(this.getCustomer().getPhone());
        this.setSuccessMessage(null);
    }
    
    /**
     * Save changes to the customer in the database and
     * query all products again to update the view
     * @return the side to navigate to
     */
    @RolesAllowed("user")
    public String saveChanges() {
        this.getCustomer().setName(this.getName());
        this.getCustomer().setPhone(this.getPhone());
        this.ejbFacade.editCustomer(this.getCustomer());
        productController.updateProducts();
        this.setSuccessMessage("Updates was succsessfull");
        return "user_profile";
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public CustomerBean getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CustomerBean ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public ProductController getProductController() {
        return productController;
    }

    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    public BidController getBidController() {
        return bidController;
    }

    public void setBidController(BidController bidController) {
        this.bidController = bidController;
    }

    public String getContinuePage() {
        return continuePage;
    }

    public void setContinuePage(String continuePage) {
        this.continuePage = continuePage;
    }
    
    
    
}
