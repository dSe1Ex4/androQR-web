package com.example.androidQr.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetPersonController {

  @RequestMapping(value = "/person_info",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseBody
  public ResponseEntity<?> getPerson(
      @RequestParam(name = "session_id") String sessionId,
      @RequestParam(name = "uuid") String uuid) {

    if (!sessionId.isEmpty() && !uuid.isEmpty()) {
      Map<String, Object> result = new HashMap<>();

      //TODO найти инфу в бд (пока не реализованно)
      result.put("eventname", "Соревнования по стрельбе");
      result.put("role", "Посетитель");
      result.put("firstname", "Олег");
      result.put("secondname", "Булдаков");
      result.put("threename", "Васильевич");

      Map<String, Object> extra = new HashMap<>();
      extra.put("item", "Вещь");
      result.put("extra", extra);
      return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    return (ResponseEntity<?>) ResponseEntity.badRequest();
  }

}
