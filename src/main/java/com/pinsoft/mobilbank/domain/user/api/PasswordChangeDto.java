package com.pinsoft.mobilbank.domain.user.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordChangeDto {

    private final String oldPassword;
    private final String newPassword;

}
