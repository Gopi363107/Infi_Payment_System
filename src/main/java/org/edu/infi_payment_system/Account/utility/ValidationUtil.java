package org.edu.infi_payment_system.Account.utility;

public class ValidationUtil {

    public static boolean isAccountNumberValid(String accNo){
        return accNo.length() == 10;
    }
}
