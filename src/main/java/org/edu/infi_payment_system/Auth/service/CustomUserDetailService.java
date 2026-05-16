package org.edu.infi_payment_system.Auth.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.User.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email)
        throws UsernameNotFoundException {

            return userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "User not found with email" + email
                            )
                    );

    }
}
