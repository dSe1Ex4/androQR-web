package com.example.androidQr.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QRCodeGenerator {

    @Value("${qr.path}")
    private String qrPath;


    public String generatedQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 255, 255, hints);

        File qrDir = new File(qrPath);

        String fileName = null;
        //UUID пока только для названия файла

        if (!qrPath.isEmpty()) {
            if (!qrDir.exists()) {
                qrDir.mkdir();
            }

            fileName = UUID.randomUUID().toString();

            Path path = FileSystems.getDefault().getPath(qrPath + "/" + fileName + ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        }

        return fileName;
    }

}
