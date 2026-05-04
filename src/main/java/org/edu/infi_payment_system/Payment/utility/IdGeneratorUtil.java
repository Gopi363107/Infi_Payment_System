package org.edu.infi_payment_system.Payment.utility;

import java.util.UUID;


public class IdGeneratorUtil {

    public static String generateTransactionId(){
        return "TXN" + UUID.randomUUID().toString();
    }
}
