package com.pinsoft.mobilbank.domain.qrcode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QrCodeDto {

    private String senderUserId;
    private String targetUserId;
    private String id;
    private Double amount;

}
