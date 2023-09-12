package com.pinsoft.mobilbank.domain.transfer.api;

import com.pinsoft.mobilbank.domain.transfer.impl.MoneyTransferStatus;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MoneyTransferDto {

    private final String id;
    private final Date createdDate;
    private final Date modified;
    private final Double amount;
    private final UserDto senderUser;
    private final UserDto targetUser;
    private final MoneyTransferStatus transferStatus;



}
