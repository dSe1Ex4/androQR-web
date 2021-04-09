package com.example.androidQr.utils;

import com.example.androidQr.exception.FileIsEmptyException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtils {

  @Value("${personimagedir.path}")
  private String personimagedir;

  public String savePersonImage(MultipartFile multipartFile, String name) throws IOException {
    if (!multipartFile.isEmpty()) {
      File uploadDir = new File(personimagedir);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      StringBuilder personImgName = new StringBuilder();
      personImgName.append(name);
      personImgName.append("-");
      personImgName.append(LocalDate.now().toString());
      personImgName.append(".");
      personImgName.append("jpg");

      // Сохраняем файл
      File imgFile = new File(personimagedir + "\\\\" + personImgName.toString());

      try (OutputStream os = new FileOutputStream(imgFile)) {
        os.write(multipartFile.getBytes());
      }

      return personimagedir + "\\\\" + personImgName.toString();
    }

    throw new FileIsEmptyException(multipartFile.getOriginalFilename());
  }


}
