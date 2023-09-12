package com.pinsoft.mobilbank.domain.transfer.impl;

import com.pinsoft.mobilbank.domain.user.impl.User;
import com.pinsoft.mobilbank.library.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = MoneyTransfer.TABLE)
public class MoneyTransfer extends AbstractEntity {

    public static final String TABLE="money-transfer";

    private Double amount;
    @OneToOne
    private User senderUser;
    @OneToOne
    private User targetUser;
    @Enumerated(EnumType.STRING)
    private MoneyTransferStatus transferStatus;




}
