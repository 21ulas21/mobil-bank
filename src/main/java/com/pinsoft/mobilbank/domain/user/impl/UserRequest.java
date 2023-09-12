package com.pinsoft.mobilbank.domain.user.impl;

import com.pinsoft.mobilbank.domain.user.api.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;



    public UserDto toDto(){
        return UserDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();
    }

}
