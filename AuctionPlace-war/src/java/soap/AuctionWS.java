/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import ejb.ProductBean;
import ejb.BidBean;
import entities.Bid;
import entities.Product;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import entities.Product.Status;
import java.util.ArrayList;
import javax.jms.Message;

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
     * Place a bid on a product
     * @param newBid the Bid a Customer makes on a Product
     */
    @WebMethod(operationName = "bidForAuction")
    public Message bidForAuction(Bid newBid) {
        bidbean.create(newBid);
        //Message message = new Message();
        return null;
    }

}
