package com.pinsoft.mobilbank.domain.user.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {

    private final String firstName;
    private final String lastName;
    private final String email;

}
