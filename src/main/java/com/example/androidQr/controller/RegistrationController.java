package com.example.androidQr.controller;

import com.example.androidQr.DTO.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegistrationController {

  @GetMapping("/registration")
  public String registration(Model model) {
    model.addAttribute("userDTO", new UserDTO());
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(@ModelAttribute UserDTO userDTO, Model model) {
    model.addAttribute("userDTO", userDTO);
    log.info(userDTO.getFirstName());
    log.info(userDTO.getName());
    log.info(userDTO.getSecondName());
    log.info(userDTO.getRole().name());
    log.info(userDTO.getEvent().name());
    log.info(userDTO.getGun().name());
    return "registration";
  }

}
