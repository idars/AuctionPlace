/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import entities.Product;
import javax.ejb.Stateless;
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
}
