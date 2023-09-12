package com.pinsoft.mobilbank.domain.user.api;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class UserDto {

    private final String id;
    private final Date createdDate;
    private final Date modified;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final boolean status;
    private final List<UserDto> friends;
    private final Double amount;

}
