package org.edu.infi_payment_system.RateLimiting.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.RateLimiting.service.RateLimitService;
import org.edu.infi_payment_system.RateLimiting.util.RateLimitKeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String key =
                RateLimitKeyGenerator.generate(request);

        boolean allowed =
                rateLimitService.tryConsume(
                        key,
                        request.getRequestURI()
                );

        if(!allowed) {

            response.setStatus(429);

            response.getWriter().write(
                    "Rate Limit Exceeded"
            );

            return;
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}