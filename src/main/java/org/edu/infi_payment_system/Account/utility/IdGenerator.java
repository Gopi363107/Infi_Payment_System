package org.edu.infi_payment_system.Account.utility;

public class IdGenerator {

    public static String GenerateAccountNumber(){
        return "ACC" + System.currentTimeMillis();
    }
}
