package com.pinsoft.mobilbank.domain.transfer.impl;

import com.google.zxing.WriterException;
import com.pinsoft.mobilbank.domain.transfer.api.MoneyTransferService;
import com.pinsoft.mobilbank.domain.transfer.api.MyMoneyTransferDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService service;


    @PostMapping()
    public ResponseEntity<byte[]> sendMoney(@RequestBody MoneyTransferRequest request) throws IOException, WriterException {
        var result = service.moneyTrasnfer(request.toDto());
        return ResponseEntity.ok().header("Content-Type","image/png").body(result);
    }
    @PostMapping("/read")
    public void takeMoney(@RequestBody MoneyTransferRequest request){
         service.takeMoneyTransfer(request.toDto());

    }
    @GetMapping("/my-transfers")
    public ResponseEntity<MyMoneyTransferDto> getMyMoneyTransfers(){
        var result = service.getMyMoneyTransfers();
        return ResponseEntity.ok(result);
    }







}
