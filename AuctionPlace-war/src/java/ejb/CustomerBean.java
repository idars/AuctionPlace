package ejb;

import entities.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.LoginHelper;

/**
 *
 * @author Daniel Losvik
 */


@Stateless
public class CustomerBean extends AbstractFacade<Customer>{

    @PersistenceContext(unitName="AuctionPlace-warPU")
    private EntityManager em;
    
    public CustomerBean() {
        super(Customer.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * check if user already exists and
     * Encrypt password with MD5 which is weak but fast to implement
     * @param customer the new customer to add
     * @return true if the customer was successfully added
     * @throws Exception if the encryption of the password failed
     */
    public boolean RegisterNewCustomer(Customer customer) throws Exception {

        if (getCustomerByEmail(customer.getEmail()) == null) {
            
            customer.setPassword(LoginHelper.encryptPassword(customer.getPassword()));
            
            super.create(customer);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Query the customer from the database and check if the given 
     * password matches the stored password
     * @param email the customers email
     * @param password the customers password
     * @return the customer
     * @throws Exception if encryption of password failed
     */
    public Customer loginCustomer(String email, String password) throws Exception {
        Customer customer = getCustomerByEmail(email);
        if(customer != null && customer.getPassword().equals(LoginHelper.encryptPassword(password))) {         
            return customer;
        }
        else {
            return null;
        }
    }

    /**
     * Query the customer from the database
     * @param email the customers email
     * @return the customer
     */
    public Customer getCustomerByEmail(String email) {
        Query createNamedQuery = getEntityManager().createNamedQuery("Customer.findByEmail");

        createNamedQuery.setParameter("email", email);

        if (createNamedQuery.getResultList().size() > 0) {
            return (Customer) createNamedQuery.getSingleResult();
        }
        else {
            return null;
        }
    }
    /**
     * Update the customer inn the database
     * @param customer
     */
    public void editCustomer(Customer customer) {
        this.edit(customer);
    }

}
