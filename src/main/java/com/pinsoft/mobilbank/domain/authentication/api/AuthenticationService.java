package com.pinsoft.mobilbank.domain.authentication.api;


import com.pinsoft.mobilbank.domain.authentication.impl.AuthenticationRequest;
import com.pinsoft.mobilbank.domain.authentication.impl.AuthenticationResponse;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
import com.pinsoft.mobilbank.domain.user.impl.UserRequest;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);
    UserDto register(UserRequest request);

}
