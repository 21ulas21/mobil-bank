package com.pinsoft.mobilbank.domain.transfer.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MyMoneyTransferDto {

    private final List<MoneyTransferDto> sendMoneyTransfer;
    private final List<MoneyTransferDto> takeMoneyTransfer;
}
