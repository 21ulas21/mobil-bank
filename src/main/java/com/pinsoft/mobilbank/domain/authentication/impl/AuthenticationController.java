package com.pinsoft.mobilbank.domain.authentication.impl;

import com.pinsoft.mobilbank.domain.authentication.api.AuthenticationService;
import com.pinsoft.mobilbank.domain.user.impl.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRequest request){
        service.register(request);
        return ResponseEntity.ok().build();
    }

}