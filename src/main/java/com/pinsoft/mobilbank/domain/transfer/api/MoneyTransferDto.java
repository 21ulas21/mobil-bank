package com.pinsoft.mobilbank.domain.transfer.api;

import com.pinsoft.mobilbank.domain.transfer.impl.MoneyTransferStatus;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
import com.pinsoft.mobilbank.domain.user.api.UserFriendsDto;
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
    private final UserFriendsDto senderUser;
    private final UserFriendsDto targetUser;
    private final MoneyTransferStatus transferStatus;



}
