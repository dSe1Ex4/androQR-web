package com.example.androidQr.controller;

import static com.example.androidQr.utils.SessionUtils.generateSecretKey;
import static com.example.androidQr.utils.SessionUtils.generateSession;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.androidQr.dto.UserDTO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class GetUserController {

  /**
   * Регистрация охраника
   *
   * @return html страница с формой регистрацией охранника
   */
  @GetMapping("/registrationUser")
  private String userRegistration() {
    return "registrationUser";
  }

  @PostMapping("/registrationUser")
  private String userRegistration(UserDTO userDTO) {

    String bcryptHashString = BCrypt.withDefaults()
        .hashToString(12, userDTO.getPassword().toCharArray());

    log.info(userDTO.getLogin());
    log.info(bcryptHashString);

    return "registrationUser";
  }

  /**
   * Авторизация охраника
   */
  @RequestMapping(value = "/auth", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseBody
  public ResponseEntity<?> authUser(UserDTO userDTO) {
    if (userDTO.getLogin().equals("login") && userDTO.getPassword().equals("login")) {
      // Генерируем сессию
      // todo занести в бд secretkey при авторизации
      String secretKey = generateSecretKey();
      String session = generateSession(secretKey, userDTO.getLogin());

      userDTO.setSession(session);
      Map<String, String> result = new HashMap<>();
      result.put("session_id", userDTO.getSession());

      return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/get_img",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = "image/*")
  @ResponseBody
  public ResponseEntity<InputStreamResource> getImg(
      @RequestParam(name = "session_id") String sessionId,
      @RequestParam(name = "uuid") String uuid)
      throws IOException {
    if (!sessionId.isEmpty() && !uuid.isEmpty()) {
      FileSystemResource file = new FileSystemResource("images/sticker.png");
      return ResponseEntity.ok()
          .contentLength(file.contentLength())
          .contentType(
              MediaType.parseMediaType(Files.probeContentType(Paths.get("images/sticker.png"))))
          .body(new InputStreamResource(file.getInputStream()));
    }

    return (ResponseEntity<InputStreamResource>) ResponseEntity.badRequest();
  }

}
