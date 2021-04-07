package com.example.androidQr.controller;

import com.example.androidQr.dto.EventDTO;
import com.example.androidQr.dto.MapParamDTOValueDTO;
import com.example.androidQr.dto.PersonDTO;
import com.example.androidQr.dto.RoleDTO;
import com.example.androidQr.dto.ValueDTO;
import com.example.androidQr.service.EventService;
import com.example.androidQr.service.ParamService;
import com.example.androidQr.service.RegistrationService;
import com.example.androidQr.service.RoleService;
import com.example.androidQr.utils.ImageUtils;
import com.google.zxing.WriterException;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PersonController {

  private final static Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

  private final ImageUtils imageUtils;
  private final RegistrationService registrationService;
  private final EventService eventService;
  private final RoleService roleService;
  private final ParamService paramService;

  @GetMapping("/registration")
  public String registration(Model model
      , @CookieValue(value = "threeName", defaultValue = "") String threeName
      , @CookieValue(value = "firstname", defaultValue = "") String firstName
      , @CookieValue(value = "secondName", defaultValue = "") String secondName
  ) {

    PersonDTO personDTO = new PersonDTO();
    personDTO.setFirstName(firstName);
    personDTO.setSecondName(secondName);
    personDTO.setThreeName(threeName);

    // Данные выбранны заранее
    model.addAttribute("personDTO", new PersonDTO());
    model.addAttribute("eventList", eventService.getEvent());
    model.addAttribute("eventDTO", new EventDTO());
    model.addAttribute("roleList", roleService.getRole(eventService.getEvent().get(0)));
    model.addAttribute("roleDTO", new RoleDTO());
    // model.addAttribute("params", );

    MapParamDTOValueDTO paramDTOValueDTO = new MapParamDTOValueDTO();
    paramService.getParam(eventService.getEvent().get(0))
        .forEach(s->{
          ValueDTO valueDTO = new ValueDTO();
          valueDTO.setParamDTO(s);
          paramDTOValueDTO.getMap().put(s, valueDTO);
        });

    model.addAttribute("mapParamDTOValueDTO", paramDTOValueDTO);

    return "registration";
  }

  @PostMapping("/registration")
  public String registration(
      @ModelAttribute PersonDTO personDTO,
      @ModelAttribute EventDTO eventDTO,
      @ModelAttribute RoleDTO roleDTO,
      @ModelAttribute MapParamDTOValueDTO mapParamDTOValueDTO, Model model,
      @RequestParam("file") MultipartFile file,
      HttpServletResponse response
  ) throws IOException, WriterException {

    // Cookie ???
    Cookie cookie = new Cookie("threeName", personDTO.getThreeName());
    response.addCookie(cookie);
    cookie = new Cookie("firstname", personDTO.getFirstName());
    response.addCookie(cookie);
    cookie = new Cookie("secondName", personDTO.getThreeName());
    response.addCookie(cookie);

    // Добавить кеширование пароля

    personDTO.setImgPath(imageUtils.savePersonImage(file,
        personDTO.getFirstName() + personDTO.getSecondName() + personDTO.getThreeName()));
    LOGGER.info(eventDTO.getName());

    String path = registrationService.registerPerson(personDTO, eventDTO, roleDTO,
        mapParamDTOValueDTO);

    // Ждем 1 секунду
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // e.printStackTrace();
    }

    model.addAttribute("fileName", path);
    return "registration";
  }
}
