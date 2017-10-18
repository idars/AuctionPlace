package ejb;

import entities.Customer;
import java.util.List;
import entities.Product;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import web.CustomerController;

/**
 *
 * @author Daniel Losvik
 */
@Stateless
public class ProductBean extends AbstractFacade<Product>{

    @PersistenceContext(unitName="AuctionPlace-warPU")
    private EntityManager em;
    
    @Resource
    private SessionContext sc;
    @Resource(lookup = "java:app/BidWinnerTopic")
    private Topic topic;
    @Inject
    private JMSContext context;
    
    @Inject
    CustomerController customerController;
    
    public ProductBean() {
        super(Product.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Query all the products in the database
     * @return a list of the products
     */
    public List<Product> getProducts() {
        return this.findAll();
    }
    
    /**
     * Add a new product to the database
     * @param product the new products to add
     */
    public void addProduct(Product product) {
        super.create(product);
    }
    
    /**
     * Update the product in the database
     * @param p the product to update
     */
    public void updateProduct(Product p) {
        this.edit(p);
    }
    
    public void sendMessageToWinner(Product p, Customer c) {
        if (c != null && p.getCurrentBid().getBidder().getEmail().equals(c.getEmail())) {
            this.context.createProducer().send(topic,
                    "Dear " + c.getName() + ",\n"
                    + "Congratulations! You have won in bidding for " + p.getName() + "\n"
                    + "You can access the product using the following link:\n"
                    + "URL = http://localhost:8080/AuctionPlace-war/product_details.xhtml?product=" + p.getId() + "\n");
        }
    }
}
