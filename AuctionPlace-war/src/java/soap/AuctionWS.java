/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import ejb.ProductBean;
import ejb.BidBean;
import ejb.CustomerBean;
import entities.Bid;
import entities.Customer;
import entities.Product;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;
import entities.Product.Status;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
@WebService(serviceName = "AuctionWS")
@Stateless()
public class AuctionWS {
    
    @EJB
    private ProductBean productbean;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")
    
    @EJB
    private BidBean bidbean;
    
    @EJB
    private CustomerBean customerbean;
    
       /**
     * Get a list of active auctions
     * @return  a list of products which are available on auction now
     */
    @WebMethod(operationName = "getActiveAuctions")
    public List<Product> getActiveAuctions() {
        List<Product> products = productbean.findAll();
        List<Product> activeProducts = new ArrayList();
        for (Product product : products) {
            if(product.getStatus() == Status.PUBLISHED) {
                activeProducts.add(product);
            }
        }
        return activeProducts;
    }

    /**
     * The webservice creates a bid for a customer (bidder) on a product
     * @param amount
     * @param customerid
     * @param productid
     * @return 
     */
    @WebMethod(operationName = "bidForAuction")
    public SetBidStatus bidForAuction(double amount, long customerid, long productid) {
 
        Customer customer = customerbean.find(customerid);
 
        Bid newBid = new Bid();
        newBid.setBidder(customer);
        newBid.setAmount(amount);
        
        Product product = productbean.find(productid);
         
        SetBidStatus status = new SetBidStatus();
        status.setCustomer(customer);
        status.setProduct(product);
        
        try { 
            // TODO: If one call succeeds, but the other fails, we have an incosistent database
            // TODO: The methods do no checks on wether this succeeds, valid datas or whatever
            product.setCurrentBid(newBid);
            bidbean.create(newBid);   

            status.setCode(200);
            return status;
        }
        catch (Exception e) {
            status.setCode(500);
            return status;
        }
    }

}
