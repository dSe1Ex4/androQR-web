package com.example.androidQr.controller;

import com.example.androidQr.dto.PersonDTO;
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
      , @CookieValue(value = "familyName", defaultValue = "") String familyName
      , @CookieValue(value = "firstname", defaultValue = "") String firstName
      , @CookieValue(value = "middleName", defaultValue = "") String middleName
  ) {
    PersonDTO personDTO = new PersonDTO();
    personDTO.setLastName(familyName);
    personDTO.setFirstName(firstName);
    personDTO.setMiddleName(middleName);
    model.addAttribute("userDTO", personDTO);
    //model.addAttribute("userDTO",new UserDTO());
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(
      @ModelAttribute PersonDTO personDTO, Model model,
      @RequestParam("file") MultipartFile file,
      HttpServletResponse response
  ) throws IOException, WriterException {
    Cookie cookie = new Cookie("familyname", personDTO.getFirstName());//
    response.addCookie(cookie);
    cookie = new Cookie("firstname", personDTO.getFirstName());//
    response.addCookie(cookie);
    cookie = new Cookie("middlename", personDTO.getMiddleName());//
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

    model.addAttribute("userDTO", personDTO);
    log.info(personDTO.getFirstName());
    log.info(personDTO.getLastName());
    log.info(personDTO.getMiddleName());
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
    textBuilder.append(personDTO.getFirstName());
    textBuilder.append(" ");
    // textBuilder.append(userDTO.getName());
    textBuilder.append(" ");
    //textBuilder.append(userDTO.getSecondName());
    textBuilder.append(" ");
    // textBuilder.append(userDTO.getRole().name());
    textBuilder.append(" ");
    textBuilder.append(personDTO.getEvent());
    textBuilder.append(" ");
    // textBuilder.append(userDTO.getGun().name());

    qrCodeGenerator.generatedQRCodeImage(textBuilder.toString());

    return "registration";
  }
}
