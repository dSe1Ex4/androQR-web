package com.example.androidQr.utils;

import com.example.androidQr.exception.FileIsEmptyException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtils {

  @Value("${personimagedir.path}")
  private String personimagedir;

  public String savePersonImage(MultipartFile file, String name) throws IOException {
    if (!file.isEmpty()) {
      File uploadDir = new File(personimagedir);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      StringBuilder personImgName = new StringBuilder();
      personImgName.append(name);
      personImgName.append("-");
      personImgName.append(LocalDate.now().toString());
      personImgName.append(".");
      personImgName.append("jpeg");

      file.transferTo(new File(personimagedir + "\\\\" + personImgName.toString()));

      return personimagedir + "\\\\" + personImgName.toString();
    }

    throw new FileIsEmptyException(file.getOriginalFilename());
  }

}
