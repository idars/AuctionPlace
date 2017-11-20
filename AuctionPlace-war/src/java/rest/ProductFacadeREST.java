/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.Bid;
import entities.Customer;
import entities.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.BidHelper;


/**
 *
 * @author Ben
 */
@Stateless
@Path("entities.product")
public class ProductFacadeREST extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "AuctionPlace-warPU")
    private EntityManager em;

    public ProductFacadeREST() {
        super(Product.class);
    }
    
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Product entity) {
        super.create(entity);
    }
    
    @POST
    @Path("/login")
    public Response login(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        
        String[] auth = Authentication.authenticate(request.getHeader("Authorization"));
        String email = auth[0];
        String password = auth[1];
        String isValid = "false";
         
        try {
            Query q = getEntityManager().createNamedQuery("Customer.findByEmail");
            q.setParameter("email", email);  
            
            if (q.getResultList().size() > 0) {
                Customer c = (Customer) q.getSingleResult();
                if (c.getPassword().equals(password)) {
                    isValid = "true";
                }
            }
         } catch (Exception e) {
             System.out.println(e);
         }
        return Response.ok(isValid).build();
    }

    
    @PUT
    @Path("/sendBid")
    public Response sendBid(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        //response.setHeader("Access-Control-Allow-Origin", "*");
        
        String[] auth = Authentication.authenticate(request.getHeader("Authorization"));
        String email = auth[0];
        String password = auth[1];
        
        Query createNamedQuery = getEntityManager().createNamedQuery("Product.findById");
        createNamedQuery.setParameter("id", Long.parseLong(request.getParameter("id")));
        Product p = (Product) createNamedQuery.getSingleResult();
        
        Query q = getEntityManager().createNamedQuery("Customer.findByEmail");
        q.setParameter("email", email);  
        Customer c = (Customer) q.getSingleResult();
        
        Boolean isValid = false;
        if(!request.getParameter("amount").equals("")) {
            isValid = BidHelper.validateBid(p, Double.parseDouble(request.getParameter("amount")));
            if(isValid) {
                Bid b = new Bid(Double.parseDouble(request.getParameter("amount")), 0.0, false, c);
                p.setCurrentBid(b);
            }
        }
        return Response.ok(isValid).build();
    }

    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id, @Context HttpServletRequest request) {

        String[] usernamePassword;
        usernamePassword = Authentication.authenticate(request.getHeader("Authorization"));
        
        //if(false) super.remove(super.find(id));
        
        String username, password;
         try {
            username = usernamePassword[0];
            password = usernamePassword[1];
            Query createNamedQuery = getEntityManager().createNamedQuery("Customer.findByEmail");
            createNamedQuery.setParameter("email", username);
        if (createNamedQuery.getResultList().size() > 0) {
            Customer c = ((Customer) createNamedQuery.getSingleResult());
             if (c.getPassword().equals(password)) {
                 super.remove(super.find(id));
             }
        }

         } catch (Exception e) {
             System.out.println ("Can't decode password");
             //return false;
         }
         
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Product find(@PathParam("id") Long id, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return super.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findAll(@Context HttpServletResponse response) {
        //response.setHeader("Access-Control-Allow-Origin", "*");
        return super.findAll();
    }
    
    @GET
    @Path("filter")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findFiltered(@QueryParam("customerID") Long customerID) {
        List<Product> products = findAll();
        
        if (products != null && customerID != null) {
            for (Product p : products) {
                if (p != null) {
                    Long ownerID = p.getOwner().getId();
                    Long bidderID = p.getCurrentBid().getBidder().getId();

                    if (p.getStatus() != Product.Status.PUBLISHED 
                            || ownerID.equals(customerID) 
                            || bidderID.equals(customerID)) {
                        products.remove(p);
                    }
                }
            }
        }
        
        return products;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
