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
import com.google.common.net.HttpHeaders;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PersonController {

  private final static Logger LOGGER = LoggerFactory.getLogger(PersonController.class);


  private final RegistrationService registrationService;
  private final EventService eventService;
  private final RoleService roleService;
  private final ParamService paramService;
  private final ImageUtils imageUtils;

  @GetMapping("/index")
  public String getIndex(Model model) {
    List<EventDTO> eventDTOList = eventService.getAll();
    model.addAttribute(eventDTOList);
    return "index";
  }

  @PostMapping("/index")
  public String getIndexPost(Model model, @ModelAttribute(name = "event") String eventId,
      RedirectAttributes redirectAttrs) {
    System.out.println(eventId);
    redirectAttrs.addFlashAttribute("eventId", eventId);
    return "redirect:/person/registration";
  }


  @GetMapping("/registration")
  public String registration(Model model
//      , @CookieValue(value = "threeName", defaultValue = "") String threeName
//      , @CookieValue(value = "firstname", defaultValue = "") String firstName
//      , @CookieValue(value = "secondName", defaultValue = "") String secondName
      , HttpSession session
  ) throws Exception {

//    PersonDTO personDTO = new PersonDTO();
//    personDTO.setFirstName(firstName);
//    personDTO.setSecondName(secondName);
//    personDTO.setThreeName(threeName);

    // Данные выбранны заранее
    EventDTO eventDTO = eventService.getEvent(Integer.valueOf(
        (String) model.getAttribute("eventId")));

    session.setAttribute("eventId",(String) model.getAttribute("eventId"));
    model.addAttribute("eventDTO", model.getAttribute("eventId"));
    model.addAttribute("personDTO", new PersonDTO());
    model.addAttribute("roleList",
        roleService.getRole(eventDTO));
    model.addAttribute("roleDTO", new RoleDTO());

    MapParamDTOValueDTO paramDTOValueDTO = new MapParamDTOValueDTO();
    paramService.getParam(eventDTO)
        .forEach(s -> {
          ValueDTO valueDTO = new ValueDTO();
          valueDTO.setParamDTO(s);
          paramDTOValueDTO.getParamDTOValueDTOMap().put(s.getName(), valueDTO.getValue());
        });

    model.addAttribute("mapParamDTOValueDTO", paramDTOValueDTO);

    return "registration";
  }

  @PostMapping("/registration")
  public String registration(
      @ModelAttribute PersonDTO personDTO,
      @ModelAttribute(name = "role") String roleId,
      @ModelAttribute(name = "value") MapParamDTOValueDTO mapParamDTOValueDTO,
      Model model,
      @RequestParam("file") MultipartFile file,
      HttpSession session,
      HttpServletResponse response
  ) throws Exception {

    Cookie cookie = new Cookie("threeName", personDTO.getThreeName());
    response.addCookie(cookie);
    cookie = new Cookie("firstname", personDTO.getFirstName());
    response.addCookie(cookie);
    cookie = new Cookie("secondName", personDTO.getThreeName());
    response.addCookie(cookie);

    String eventId = (String) session.getAttribute("eventId");


//    String eventId = (String) model.getAttribute("eventId");
    String path = registrationService.registerPerson(personDTO, eventId, roleId,
        mapParamDTOValueDTO, file);

    // Ждем 1 секунду
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // e.printStackTrace();
    }

    // FileSystemResource qr_file = new FileSystemResource(new File(path));

    // model.addAttribute("fileName",qr_file.getFile());

    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();

    File file1 = new File(path);
    FileInputStream in = new FileInputStream(file1);
    byte[] content = new byte[(int) file1.length()];
    in.read(content);
    ServletContext sc = request.getSession().getServletContext();
    String mimetype = sc.getMimeType(file.getName());
    response.reset();
    response.setContentType(mimetype);
    response.setContentLength(content.length);
    response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + ".png" + "\"");
    org.springframework.util.FileCopyUtils.copy(content, response.getOutputStream());

    return "result";
  }



}
