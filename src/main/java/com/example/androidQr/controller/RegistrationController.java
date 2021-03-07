package com.example.androidQr.controller;

import com.example.androidQr.dto.UserDTO;
import com.example.androidQr.generator.QRCodeGenerator;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
  // private final HttpServletResponse response;

  @Value("${upload.path}")
  private String uploadPath;

  @GetMapping("/registration")
  public String registration(Model model
      , @CookieValue(value = "familyName", defaultValue = "Черепчепчанов") String familyName
      , @CookieValue(value = "firstname", defaultValue = "Алег") String firstName
      , @CookieValue(value = "middleName", defaultValue = "Сергейгеевич") String middleName
  ) {
    UserDTO userDTO = new UserDTO();
    userDTO.setLastName(familyName);
    userDTO.setFirstName(firstName);
    userDTO.setMiddleName(middleName);
    model.addAttribute("userDTO", userDTO);
    //model.addAttribute("userDTO",new UserDTO());
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(
      @ModelAttribute UserDTO userDTO, Model model,
      @RequestParam("file") MultipartFile file,
      HttpServletResponse response
  ) throws IOException, WriterException {
    Cookie cookie = new Cookie("familyname", userDTO.getFirstName());//
    response.addCookie(cookie);
    cookie = new Cookie("firstname", userDTO.getFirstName());//
    response.addCookie(cookie);
    cookie = new Cookie("middlename", userDTO.getMiddleName());//
    response.addCookie(cookie);
    if (!file.isEmpty()) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      String uuidFile = UUID.randomUUID().toString();
      String resultFileName = uuidFile + "." + file.getOriginalFilename();

      file.transferTo(new File(uploadPath + "\\\\" + resultFileName));
    }

    model.addAttribute("userDTO", userDTO);
    log.info(userDTO.getFirstName());
    log.info(userDTO.getLastName());
    log.info(userDTO.getMiddleName());
    // log.info(userDTO.getRole().name());
    // log.info(userDTO.getEvent().name());
    // log.info(userDTO.getGun().name());
    // log.info(userDTO.getFileName());
    // log.info(userDTO.getName());
    // log.info(userDTO.getSecondName());
    // log.info(userDTO.getRole().name());
    // log.info(userDTO.getEvent());
    // log.info(userDTO.getGun().name());

    StringBuilder textBuilder = new StringBuilder();
    textBuilder.append(userDTO.getFirstName());
    textBuilder.append(" ");
    // textBuilder.append(userDTO.getName());
    textBuilder.append(" ");
    //textBuilder.append(userDTO.getSecondName());
    textBuilder.append(" ");
    // textBuilder.append(userDTO.getRole().name());
    textBuilder.append(" ");
    textBuilder.append(userDTO.getEvent());
    textBuilder.append(" ");
    // textBuilder.append(userDTO.getGun().name());

    qrCodeGenerator.generatedQRCodeImage(textBuilder.toString());

    return "registration";
  }
}
