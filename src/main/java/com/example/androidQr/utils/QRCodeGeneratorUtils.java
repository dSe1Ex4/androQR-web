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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QRCodeGeneratorUtils {

  @Value("${qr.path}")
  private String qrPath;

  public String generatedQRCodeImage(String text, String fileName)
      throws WriterException, IOException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();

    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 255, 255, hints);

    File qrDir = new File(qrPath);

    String filePath = null;

    if (!qrPath.isEmpty()) {
      if (!qrDir.exists()) {
        qrDir.mkdir();
      }

      filePath = qrPath + "/" + fileName + ".png";

      Path path = FileSystems.getDefault().getPath(filePath);
      MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    return filePath;
  }




}
