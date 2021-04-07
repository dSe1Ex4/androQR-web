package com.example.androidQr.controller;

import com.example.androidQr.dto.PersonDTO;
import com.example.androidQr.service.AuthorizationService;
import com.example.androidQr.service.PersonService;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

  private static final String UNAUTHORIZED_MESSAGE = "Ошибка авторизации. Проверте логин/пароль";

  private final PersonService personService;
  private final AuthorizationService authorizationService;

  /**
   * Метод получение данных о человеке по UUID
   *
   * @param sessionId сессия
   * @param uuid      UUID
   * @return данные о человеке
   */
  @RequestMapping(value = "/person_info",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseBody
  public ResponseEntity<?> getPerson(
      @RequestParam(name = "session_id") String sessionId,
      @RequestParam(name = "uuid") String uuid) throws Exception {

    return personService.getMeasurePersonInfo(sessionId, uuid);
  }

  /**
   * Авторизация охраника
   *
   * @param login    логин
   * @param password хеш пароля
   * @return сессия
   */
  @RequestMapping(value = "/auth", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseBody
  public ResponseEntity<?> authUser(
      @RequestParam(name = "login") String login,
      @RequestParam(name = "password") String password
  ) throws Exception {

    Map<String, String> result = authorizationService.authorization(login, password);
    if (result != null) {
      return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
    return new ResponseEntity<>(UNAUTHORIZED_MESSAGE, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Получение изображения по UUID
   *
   * @param sessionId сессия
   * @param uuid      UUID
   * @return изображение поситителя мероприятия
   */
  @RequestMapping(value = "/get_img",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = {"image/*", MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  @ResponseBody
  public ResponseEntity<?> getImg(
      @RequestParam(name = "session_id") String sessionId,
      @RequestParam(name = "uuid") String uuid)
      throws Exception {
    return personService.getPersonImage(sessionId, uuid);
  }
}
