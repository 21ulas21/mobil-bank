package com.pinsoft.mobilbank.domain.qrcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pinsoft.mobilbank.domain.transfer.api.MoneyTransferDto;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrService {

    public String decodeQr(byte[] data) throws IOException, NotFoundException {
        Result result = new MultiFormatReader()
                .decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(
                        ImageIO.read(new ByteArrayInputStream(data))))));
        return result != null ? result.getText() : null;
    }

    public byte[] generateQrCode(MoneyTransferDto dto) throws IOException, WriterException {
        QrCodeDto qrCodeDto = codeDto(dto);
        ObjectMapper objectMapper = new ObjectMapper();
        String transfer = objectMapper.writeValueAsString(qrCodeDto);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(transfer,BarcodeFormat.QR_CODE,200,200);
        ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix),"png",outputStream);
        return outputStream.toByteArray();

    }

    private QrCodeDto codeDto(MoneyTransferDto dto){
        return QrCodeDto.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .senderUserId(dto.getSenderUser().getId())
                .targetUserId(dto.getTargetUser().getId())
                .build();
    }



}
