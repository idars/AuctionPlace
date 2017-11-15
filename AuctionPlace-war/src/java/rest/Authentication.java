/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import ejb.CustomerBean;
import entities.Customer;
import java.util.Base64;
import javax.ejb.EJB;
import util.LoginHelper;

/**
 *
 * @author Ben
 */
public class Authentication {
    
    public static String[] authenticate (String rawString) {
        String base64encodedString = rawString.substring(6);
		
         // Decode
         byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
	
         String[] usernamePassword;
         try {
         String decodedString =  new String(base64decodedBytes, "utf-8");
         usernamePassword = decodedString.split(":");
         } catch (Exception e) {
             System.out.println ("Can't decode password");
             return null;
         }
         
         return usernamePassword;
         
         
    }
    
}
