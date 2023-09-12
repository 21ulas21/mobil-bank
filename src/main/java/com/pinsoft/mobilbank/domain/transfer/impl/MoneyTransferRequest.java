package com.pinsoft.mobilbank.domain.transfer.impl;

import com.pinsoft.mobilbank.domain.transfer.api.MoneyTransferDto;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoneyTransferRequest {
    private final String id;
    private final Double amount;
    private final String senderUserId;
    private final String targetUserId;

    public MoneyTransferDto toDto(){
      return MoneyTransferDto.builder()
              .id(id)
              .amount(amount)
              .senderUser(UserDto.builder().id(senderUserId).build())
              .targetUser(UserDto.builder().id(targetUserId).build())
              .build();
    }


}
