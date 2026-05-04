package org.edu.infi_payment_system.Payment.utility;

public class SignatureUtil {

    public static String generateSignature(String data){
        return Integer.toHexString(data.hashCode());
    }
}
