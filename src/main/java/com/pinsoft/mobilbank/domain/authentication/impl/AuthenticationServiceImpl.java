package com.pinsoft.mobilbank.domain.authentication.impl;

import com.pinsoft.mobilbank.domain.authentication.api.AuthenticationService;
import com.pinsoft.mobilbank.domain.authentication.api.UserRegisterDto;
import com.pinsoft.mobilbank.domain.user.api.UserService;
import com.pinsoft.mobilbank.library.security.jwt.JwtService;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
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
    private final UserService userService;
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
    public UserDto register(UserRegisterDto dto) {
        var userDto = toUserDto(dto);
       return userService.createUser(userDto);
    }

    public UserDto toUserDto(UserRegisterDto dto){
        return UserDto.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .amount(0.0)
                .status(true)
                .build();

    }


}
