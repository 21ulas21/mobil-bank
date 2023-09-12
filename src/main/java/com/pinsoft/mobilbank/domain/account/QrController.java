package com.pinsoft.mobilbank.domain.account;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*@RestController
@RequestMapping("/qr")
@AllArgsConstructor*/
public class QrController {

   /* private final QrService qrCodeService;*/

//    @PostMapping(path = "/api/qr/generate", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void generateQr(@RequestBody QrCodeDto request, HttpServletResponse response) throws MissingRequestValueException, WriterException, IOException {
//        if( request==null || request.getQrString()==null || request.getQrString().trim().equals("") ) {
//            throw new MissingRequestValueException("QR String is required");
//        }
//        qrCodeService.generateQr(request.getQrString(), response.getOutputStream());
//        response.getOutputStream().flush();
//    }
 /*   @PostMapping(path = "/api/qr/decode")
    public DecodedQrResponse decodeQr(@RequestParam("qrCode") MultipartFile qrCode) throws IOException, NotFoundException {
        String qrCodeString =  qrCodeService.decodeQr(qrCode.getBytes());
        return new DecodedQrResponse(qrCodeString);
    }*/

}
