package com.pinsoft.mobilbank.domain.authentication.config;

import com.pinsoft.mobilbank.domain.user.impl.UserRepository;
import com.pinsoft.mobilbank.domain.user.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("User Not Found!"));

        return new CustomUserDetails(user);
    }
}
