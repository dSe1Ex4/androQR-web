package com.example.androidQr.controller;

import com.example.androidQr.dto.UserDTO;
import com.example.androidQr.generator.QRCodeGenerator;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RegistrationController {

  private final QRCodeGenerator qrCodeGenerator;

  @Value("${upload.path}")
  private String uploadPath;

  @GetMapping("/registration")
  public String registration(Model model) {
    model.addAttribute("userDTO", new UserDTO());
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(
      @ModelAttribute UserDTO userDTO, Model model,
      @RequestParam("file") MultipartFile file
  ) throws IOException, WriterException {
    if (!file.isEmpty()) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      String uuidFile = UUID.randomUUID().toString();
      String resultFileName = uuidFile + "." + file.getOriginalFilename();

      file.transferTo(new File(uploadPath + "\\\\" + resultFileName));

      userDTO.setFileName(resultFileName);
    }

    model.addAttribute("userDTO", userDTO);
    log.info(userDTO.getFirstName());
    log.info(userDTO.getName());
    log.info(userDTO.getSecondName());
    log.info(userDTO.getRole().name());
    log.info(userDTO.getEvent().name());
    log.info(userDTO.getGun().name());
    log.info(userDTO.getFileName());

    StringBuilder textBuilder = new StringBuilder();
    textBuilder.append(userDTO.getFirstName());
    textBuilder.append(" ");
    textBuilder.append(userDTO.getName());
    textBuilder.append(" ");
    textBuilder.append(userDTO.getSecondName());
    textBuilder.append(" ");
    textBuilder.append(userDTO.getRole().name());
    textBuilder.append(" ");
    textBuilder.append(userDTO.getEvent().name());
    textBuilder.append(" ");
    textBuilder.append(userDTO.getGun().name());

    qrCodeGenerator.generatedQRCodeImage(textBuilder.toString());

    return "registration";
  }
}
