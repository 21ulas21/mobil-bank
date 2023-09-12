package com.pinsoft.mobilbank.domain.user.impl;


import com.pinsoft.mobilbank.domain.user.api.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class UserResponse {
    private final String id;
    private final Date createdDate;
    private final Date modified;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean status;

    private final Double amount;

    public static UserResponse toResponse(UserDto dto){
        return UserResponse.builder()
                .id(dto.getId())
                .createdDate(dto.getCreatedDate())
                .modified(dto.getModified())
                .amount(dto.getAmount())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }
}
