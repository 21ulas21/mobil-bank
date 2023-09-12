package com.pinsoft.mobilbank.domain.account;

import org.springframework.web.multipart.MultipartFile;

public class DecodeQrRequest {

    private MultipartFile qrCode;

    public MultipartFile getQrCode() {
        return qrCode;
    }

    public void setQrCode(MultipartFile qrCode) {
        this.qrCode = qrCode;
    }
}
