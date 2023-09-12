package com.pinsoft.mobilbank.domain.transfer.impl;

import com.pinsoft.mobilbank.domain.transfer.api.MoneyTransferDto;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
import com.pinsoft.mobilbank.domain.user.impl.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MoneyTransferResponse {

    private final String id;
    private final Date createdDate;
    private final Date modified;
    private final Double amount;
    private final UserDto senderUser;
    private final UserDto targetUser;
    private final MoneyTransferStatus transferStatus;

    public static MoneyTransferResponse fromDto(MoneyTransferDto dto){
      return MoneyTransferResponse.builder()
                .id(dto.getId())
                .createdDate(dto.getCreatedDate())
                .modified(dto.getModified())
                .amount(dto.getAmount())
                .senderUser(dto.getSenderUser())
                .targetUser(dto.getTargetUser())
                .transferStatus(dto.getTransferStatus())
                .build();

    }
}
