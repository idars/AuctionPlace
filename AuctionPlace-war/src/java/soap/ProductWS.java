/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import ejb.ProductBean;
import entities.Product;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Ben
 */
@WebService(serviceName = "ProductWS")
@Stateless()
public class ProductWS {

    @EJB
    private ProductBean ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") Product entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") Product entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") Product entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Product find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Product> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "getProducts")
    public List<Product> getProducts() {
        return ejbRef.getProducts();
    }

    @WebMethod(operationName = "addProduct")
    @Oneway
    public void addProduct(@WebParam(name = "product") Product product) {
        ejbRef.addProduct(product);
    }

    @WebMethod(operationName = "updateProduct")
    @Oneway
    public void updateProduct(@WebParam(name = "p") Product p) {
        ejbRef.updateProduct(p);
    }
    
}
