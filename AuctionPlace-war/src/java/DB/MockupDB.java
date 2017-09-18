/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import entities.Customer;

/**
 *
 * @author Daniel Losvik
 * 
 * Mockup Database, all data is deleted on re run.
 */
public class MockupDB {
    
    private static Customer[] customers = new Customer[20];
    private static int numberOfCustomers = 0;
    
    public static void addNewCustomer(Customer c) {
        customers[numberOfCustomers] = c;
        numberOfCustomers++;
    }
    
    public static Customer getCustomer(String email, String password) {
        for(int i = 0; i < numberOfCustomers; i++) {
            if(customers[i].getEmail().equals(email) &&
                    customers[i].getPassword().equals(password)) {
                return customers[i];
            }
        }
        return null;
    }
}
