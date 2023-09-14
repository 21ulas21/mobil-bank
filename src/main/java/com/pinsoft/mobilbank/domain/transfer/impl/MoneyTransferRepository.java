package com.pinsoft.mobilbank.domain.transfer.impl;

import com.pinsoft.mobilbank.domain.user.impl.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer,String> {
    List<MoneyTransfer> findMoneyTransferBySenderUser(User user);
    List<MoneyTransfer> findMoneyTransferByTargetUser(User user);

    @Query("SELECT e FROM MoneyTransfer e WHERE e.transferStatus IN ('PENDING', 'APPROVED')")
    List<MoneyTransfer> findPendingAndApprovedTransfers();
}
