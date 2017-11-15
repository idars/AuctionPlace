/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.Customer;
import entities.Product;
import java.util.Base64;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


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

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Product entity) {
        super.edit(entity);
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
    @Produces({MediaType.APPLICATION_JSON})
    public List<Product> findAll(@Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return super.findAll();
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
