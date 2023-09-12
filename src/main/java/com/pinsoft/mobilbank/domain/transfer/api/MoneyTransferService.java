package com.pinsoft.mobilbank.domain.transfer.api;

import com.google.zxing.WriterException;
import com.pinsoft.mobilbank.domain.transfer.impl.MoneyTransferRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface MoneyTransferService {

    MoneyTransferDto getMoneyTransfer(String id);
    List<MoneyTransferDto> getAllMoneyTransfer();
    MyMoneyTransferDto getMyMoneyTransfers();

    byte[] moneyTrasnfer(MoneyTransferDto dto) throws IOException, WriterException;
    MoneyTransferDto canceledMoneyTransfer(String id);

    void takeMoneyTransfer(MoneyTransferDto dto);
}
