package com.pinsoft.mobilbank.domain.transfer.impl;

import com.google.zxing.WriterException;
import com.pinsoft.mobilbank.domain.qrcode.QrService;
import com.pinsoft.mobilbank.domain.transfer.api.MoneyTransferDto;
import com.pinsoft.mobilbank.domain.transfer.api.MoneyTransferService;
import com.pinsoft.mobilbank.domain.transfer.api.MyMoneyTransferDto;
import com.pinsoft.mobilbank.domain.user.api.UserDto;
import com.pinsoft.mobilbank.domain.user.impl.User;
import com.pinsoft.mobilbank.domain.user.impl.UserServiceImpl;
import com.pinsoft.mobilbank.library.exception.InsufficientBalance;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
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
        if (!senderUser.getFriends().contains(targetUser)){
            throw new IllegalStateException("You can only send money to your friends");
        }
        if (transfer.getSenderUser().getAmount()>=dto.getAmount() && status){

            transfer.setTransferStatus(MoneyTransferStatus.PENDING);
            var moneyTransfer =  repository.save(transfer);
            return qrService.generateQrCode(toDto(moneyTransfer));
        }else {
            throw new InsufficientBalance("Insufficient Balance");
        }


    }

    @Override
    public MoneyTransferDto canceledMoneyTransfer(String id) {
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
        return toDto(repository.save(moneyTransfer));



    }

    @Override
    public void takeMoneyTransfer(MoneyTransferDto dto) {
        MoneyTransfer moneyTransfer = repository.findById(dto.getId()).orElseThrow(()-> new EntityNotFoundException("Not Found"));
        Double amount = dto.getAmount();
        UserDto targetUser = userService.getUserById(dto.getTargetUser().getId());
        UserDto senderUser = userService.getUserById(dto.getSenderUser().getId());
        UserDto currentUser = userService.getAuthenticateUser();
        if (moneyTransfer.getTransferStatus() == MoneyTransferStatus.REJECTED || moneyTransfer.getTransferStatus() == MoneyTransferStatus.APPROVED){
            throw new IllegalStateException("The transaction has already been carried out");
        }
        if (currentUser.getId().equals(targetUser.getId())){
           var senderAmount = userService.decAmount(senderUser.getId(), amount);
           var targetAmount =  userService.incAmount(targetUser.getId(), amount);
            moneyTransfer.setTransferStatus(MoneyTransferStatus.APPROVED);
            moneyTransfer.setTargetLastAmount(targetAmount);
            moneyTransfer.setSenderLastAmount(senderAmount);
            repository.save(moneyTransfer);
        }else {
            throw new IllegalStateException("You can only perform actions created just for you");
        }
    }

    private MoneyTransfer toEntity(MoneyTransfer moneyTransfer,MoneyTransferDto dto){
        moneyTransfer.setAmount(dto.getAmount());
        moneyTransfer.setSenderUser(userService.getUserEntityById(dto.getSenderUser().getId()));
        moneyTransfer.setTargetUser(userService.getUserEntityById(dto.getTargetUser().getId()));
        moneyTransfer.setSenderLastAmount(dto.getSenderLastAmount() == null ? null : dto.getSenderLastAmount());
        moneyTransfer.setTargetLastAmount(dto.getTargetLastAmount() == null ? null : dto.getTargetLastAmount());
        return moneyTransfer;
    }
    private MoneyTransferDto toDto(MoneyTransfer moneyTransfer){
        return MoneyTransferDto.builder()
                .id(moneyTransfer.getId())
                .modified(moneyTransfer.getModified())
                .createdDate(moneyTransfer.getCreatedDate())
                .amount(moneyTransfer.getAmount())
                .targetUser(userService.toUserFriendsDto(moneyTransfer.getTargetUser()))
                .senderUser(userService.toUserFriendsDto(moneyTransfer.getSenderUser()))
                .senderLastAmount(moneyTransfer.getSenderLastAmount() == null ? null : moneyTransfer.getSenderLastAmount())
                .targetLastAmount(moneyTransfer.getTargetLastAmount() == null ? null : moneyTransfer.getTargetLastAmount())
                .build();

    }


}
