/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Bid;
import entities.Customer;
import entities.Product;
import entities.Product.Status;
import java.sql.Timestamp;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daniel Losvik
 */
@Singleton
@Startup
public class LoadData {

    @PersistenceContext(unitName = "AuctionPlace-warPU")
    private EntityManager em;

    @PostConstruct
    public void createData() {
        Customer user = new Customer("Kari", "kari@gmail.com", "12345678", "12345678", 0.0);
        Customer user2 = new Customer("Bob", "bob@gmail.com", "12345678", "12345678", 0.0);
        
        Timestamp timeleft1 = new Timestamp(System.currentTimeMillis() + 500 * 3600000 + 500 * 60000);
        Timestamp timeleft2 = new Timestamp(System.currentTimeMillis() + 200 * 3600000 + 200 * 60000);
        
        Product product1 = new Product("Bibel", "http://www.pngmart.com/files/1/Book-PNG-HD.png",
                "A cool bibel", 0, timeleft1 , Status.PUBLISHED, user);
        Product product2 = new Product("Cup", "http://pngimg.com/uploads/cup/cup_PNG1980.png",
                "A cup to drink from", 0, timeleft1 , Status.PUBLISHED, user);
        Product product3 = new Product("Teddy bear", "http://www.freeiconspng.com/uploads/teddy-bear-png-33.png",
                "A Teddy bear", 0, timeleft2 , Status.PUBLISHED, user2);
        Product product4 = new Product("Car", "http://pngimg.com/uploads/audi/audi_PNG1762.png",
                "A car", 0, timeleft2 , Status.PUBLISHED, user2);
        
        Bid startingBid = new Bid(20.0, 0.0, false, user);
        Bid startingBid2 = new Bid(35.0, 0.0, false, user);
        Bid startingBid3 = new Bid(15.0, 0.0, false, user2);
        Bid startingBid4 = new Bid(5.0, 0.0, false, user2);
        
        product1.setCurrentBid(startingBid);
        product2.setCurrentBid(startingBid2);
        product3.setCurrentBid(startingBid3);
        product4.setCurrentBid(startingBid4);
        
        em.persist(user);
        em.persist(user2);
        
        em.persist(product1);
        em.persist(product2);
        em.persist(product3);
        em.persist(product4);
    }

}
