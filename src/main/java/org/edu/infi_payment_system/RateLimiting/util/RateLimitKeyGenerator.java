package org.edu.infi_payment_system.RateLimiting.util;

import jakarta.servlet.http.HttpServletRequest;

public class RateLimitKeyGenerator {

    public static String generate(HttpServletRequest request){

        String userId = request.getHeader("X-USER-ID");

        if(userId != null){
            return "RATE_LIMIT:USER:" + userId;
        }

        return "RATE_LIMIT:IP:" + request.getRemoteAddr();
    }
}
