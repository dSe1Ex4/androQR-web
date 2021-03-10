package com.example.androidQr.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetPersonController {

  @RequestMapping(value = "/person_info",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseBody
  public ResponseEntity<MultiValueMap<String, Object>> getPerson(String sessionId, String uuid) {

    MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
    //TODO найти инфу в бд (пока не реализованно)

    multiValueMap.add("eventname", "Соревнования по стрельбе");
    multiValueMap.add("role", "Посетитель");
    multiValueMap.add("firstname", "Олег");
    multiValueMap.add("secondname", "Булдаков");
    multiValueMap.add("threename", "Васильевич");

    // Передаём файл
    multiValueMap.add("img", new FileSystemResource("D:\\git\\androQR-web\\images\\sticker.png"));

    Map<String, Object> extra = new HashMap<>();
    extra.put("weapon", "мачете");

    multiValueMap.add("extra", extra);

    return new ResponseEntity<MultiValueMap<String, Object>>(multiValueMap,
        HttpStatus.OK);
  }

}
