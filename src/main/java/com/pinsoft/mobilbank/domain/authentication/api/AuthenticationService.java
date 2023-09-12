package com.pinsoft.mobilbank.domain.authentication.api;


import com.pinsoft.mobilbank.domain.authentication.impl.AuthenticationRequest;
import com.pinsoft.mobilbank.domain.authentication.impl.AuthenticationResponse;
import com.pinsoft.mobilbank.domain.user.api.UserDto;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);
    UserDto register(UserRegisterDto dto);

}
