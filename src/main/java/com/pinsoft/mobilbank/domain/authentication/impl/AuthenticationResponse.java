package com.pinsoft.mobilbank.domain.authentication.impl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    private String token;
    private boolean status;

}
