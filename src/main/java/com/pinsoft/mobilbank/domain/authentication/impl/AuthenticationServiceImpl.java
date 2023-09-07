package com.pinsoft.mobilbank.domain.authentication.impl;

import com.pinsoft.mobilbank.domain.authentication.api.AuthenticationService;
import com.pinsoft.mobilbank.domain.authentication.jwt.JwtService;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
import com.pinsoft.mobilbank.domain.user.impl.User;
import com.pinsoft.mobilbank.domain.user.impl.UserRequest;
import com.pinsoft.mobilbank.domain.user.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userService.getUserByEmail(request.getEmail());
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .status(user.isStatus())
                .token(token)
                .build();
    }

    @Override
    public UserDto register(UserRequest request) {
        User user =  userService.toEntity(new User(), request.toDto());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

       return userService.createUser(user);
    }
}
