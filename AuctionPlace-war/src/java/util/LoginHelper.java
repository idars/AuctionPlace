package util;

import java.security.MessageDigest;

/**
 *
 * @author Daniel Losvik
 */
public class LoginHelper {
    
    /**
     * Encrypt password with MD5 which is weak but fast to implement
     * @param password the customers password
     * @return the encrypted password
     * @throws Exception if encryption failed
     */
    public static String encryptPassword(String password) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}

