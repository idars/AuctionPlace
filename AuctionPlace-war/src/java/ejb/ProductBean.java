/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Customer;
import entities.Product;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daniel Losvik
 */
@Stateless
public class ProductBean extends AbstractFacade<Product>{

    @PersistenceContext(unitName="AuctionPlace-warPU")
    private EntityManager em;
    
    public ProductBean() {
        super(Product.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
