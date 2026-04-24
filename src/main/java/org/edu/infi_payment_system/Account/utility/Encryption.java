package org.edu.infi_payment_system.Account.utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encryption {

    private static final String SECRET = "MySecretKey@251$"; // must be 16 characters only

    public static String encrypt(String data){

        try{
            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes() , "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE ,key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        }
        catch (Exception e){
            throw new RuntimeException("Error while encrypting");
        }
    }

    public static String decrypt(String encryptData){

        try{
            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes() , "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE , key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
        }
        catch (Exception e){
            throw new RuntimeException("Error while decrypting");
        }
    }
}
