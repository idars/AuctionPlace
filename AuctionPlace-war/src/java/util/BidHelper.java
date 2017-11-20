/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Product;

/**
 *
 * @author Daniel
 */
public class BidHelper {
    
    public static Boolean validateBid(Product p, Double amount) {
        if(amount != null && amount >  p.getCurrentBid().getAmount()) {
            return true;
        }
        else {
            return false;
        }
    }
    
}
