package com.pinsoft.mobilbank.domain.transfer.impl;

import com.google.zxing.WriterException;
import com.pinsoft.mobilbank.domain.account.QrService;
import com.pinsoft.mobilbank.domain.transfer.api.MoneyTransferDto;
import com.pinsoft.mobilbank.domain.transfer.api.MoneyTransferService;
import com.pinsoft.mobilbank.domain.transfer.api.MyMoneyTransferDto;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
import com.pinsoft.mobilbank.domain.user.impl.User;
import com.pinsoft.mobilbank.domain.user.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final QrService qrService;
    private final UserServiceImpl userService;
    private final MoneyTransferRepository repository;


    @Override
    public MoneyTransferDto getMoneyTransfer(String id) {
        return null;
    }

    @Override
    public List<MoneyTransferDto> getAllMoneyTransfer() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public MyMoneyTransferDto getMyMoneyTransfers() {
        var user = userService.getCurrentUser();
        var sendMoneyTransfers = repository.findMoneyTransferBySenderUser(user).stream().map(this::toDto).toList();
        var takeMoneyTransfers = repository.findMoneyTransferByTargetUser(user).stream().map(this::toDto).toList();
        return MyMoneyTransferDto.builder().sendMoneyTransfer(sendMoneyTransfers).takeMoneyTransfer(takeMoneyTransfers).build();
    }

    public byte[] moneyTrasnfer(MoneyTransferDto dto) throws IOException, WriterException {
        MoneyTransfer transfer = toEntity(new MoneyTransfer(),dto);
        User targetUser = transfer.getTargetUser();
        User senderUser = transfer.getSenderUser();
        boolean status = senderUser.getFriends().contains(targetUser);
        if (transfer.getSenderUser().getAmount()>=dto.getAmount() && status){

            transfer.setTransferStatus(MoneyTransferStatus.PENDING);
            var moneyTransfer =  repository.save(transfer);
            return qrService.generateQrCode(toDto(moneyTransfer));
        }else {
            throw new UsernameNotFoundException("Yeterli bakye bulunamadı");
        }


    }

    @Override
    public void canceledMoneyTransfer(String id) {
        MoneyTransfer moneyTransfer = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Not Found"));
        Double amount = moneyTransfer.getAmount();
        User targetUser = moneyTransfer.getTargetUser();
        User senderUser = moneyTransfer.getSenderUser();
        if (moneyTransfer.getTransferStatus().equals(MoneyTransferStatus.APPROVED)){
            userService.incAmount(senderUser.getId(),amount);
            userService.decAmount(targetUser.getId(),amount);
            moneyTransfer.setTransferStatus(MoneyTransferStatus.REJECTED);
        }else if (moneyTransfer.getTransferStatus().equals(MoneyTransferStatus.PENDING)){
            moneyTransfer.setTransferStatus(MoneyTransferStatus.REJECTED);
        }


    }

    @Override
    public void takeMoneyTransfer(MoneyTransferDto dto) {
        MoneyTransfer moneyTransfer = repository.findById(dto.getId()).orElseThrow(()-> new EntityNotFoundException("Not Found"));
        Double amount = dto.getAmount();
        UserDto targetUser = dto.getTargetUser();
        UserDto senderUser = dto.getSenderUser();
        UserDto currentUser = userService.getAuthenticateUser();

        if (currentUser.getId().equals(targetUser.getId())){
            userService.decAmount(senderUser.getId(), amount);
            userService.incAmount(targetUser.getId(), amount);
            moneyTransfer.setTransferStatus(MoneyTransferStatus.APPROVED);
            repository.save(moneyTransfer);
        }else {
            throw new IllegalStateException("You can only perform actions created just for you");
        }
    }

    private MoneyTransfer toEntity(MoneyTransfer moneyTransfer,MoneyTransferDto dto){
        moneyTransfer.setAmount(dto.getAmount());
        moneyTransfer.setSenderUser(userService.getUserEntityById(dto.getSenderUser().getId()));
        moneyTransfer.setTargetUser(userService.getUserEntityById(dto.getTargetUser().getId()));
        return moneyTransfer;
    }
    private MoneyTransferDto toDto(MoneyTransfer moneyTransfer){
        return MoneyTransferDto.builder()
                .id(moneyTransfer.getId())
                .modified(moneyTransfer.getModified())
                .createdDate(moneyTransfer.getCreatedDate())
                .amount(moneyTransfer.getAmount())
                .targetUser(userService.toDto(moneyTransfer.getTargetUser()))
                .senderUser(userService.toDto(moneyTransfer.getSenderUser()))
                .build();

    }


}
