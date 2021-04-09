package com.example.androidQr.service;

import com.example.androidQr.dto.PersonDTO;
import com.example.androidQr.entity.Measure;
import com.example.androidQr.entity.Value;
import com.example.androidQr.mapper.MapPersonDTOMapper;
import com.example.androidQr.mapper.PersonMapper;
import com.example.androidQr.repository.MeasureRepository;
import com.example.androidQr.repository.PersonRepository;
import com.example.androidQr.repository.ValueRepository;
import com.example.androidQr.utils.SessionUtils;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PersonService {

  private static final String UNAUTHORIZED_MESSAGE = "Ошибка авторизации. Проверте логин/пароль";
  private static final String UNAUTHORIZED_MESSAGE_SESSION = "Авторизационная сессия больше не действительна. Пожалуйста авторизируйтесь.";
  private static final String NOT_FOUND_UUID_MESSAGE = "UUID не найден. Посетитель не зарегестрирован на мероприятии";
  private static final String NOT_FOUND_IMAGE_MESSAGE = "Фотография посетиетля не найдена";


  private final SessionUtils sessionUtils;
  private final ValueRepository valueRepository;
  private final MeasureRepository measureRepository;
  private final PersonRepository personRepository;


  public ResponseEntity<?> getMeasurePersonInfo(String sessionId, String uuid) throws Exception {

    if (sessionId != null && uuid != null && sessionUtils.verifySession(sessionId)) {
      Measure measure = measureRepository.findByUuid(uuid)
          .orElse(null);
      if (measure == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_UUID_MESSAGE);
        // return new ResponseEntity<>(NOT_FOUND_UUID_MESSAGE, HttpStatus.NOT_FOUND);
      }
      List<Value> values = valueRepository.findByMeasure(measure);

      Map<String, Object> result = MapPersonDTOMapper.mapToDTO(measure, values).getMap();
      return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_MESSAGE_SESSION);
  }

  public ResponseEntity<?> getPersonImage(String sessionId, String uuid) throws Exception {
    if (sessionId != null && uuid != null && sessionUtils.verifySession(sessionId)) {
      Measure measure = measureRepository.findByUuid(uuid)
          .orElse(null);
      if (measure == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_UUID_MESSAGE);
      }

      PersonDTO personDTO = PersonMapper.mapToDTO(personRepository.findById(measure.getPerson().getId())
          .orElseThrow(() -> new Exception(
                  "Person with id=" + measure.getPerson().getId() + " was not found")));

      try{
        FileSystemResource file = new FileSystemResource(personDTO.getImgPath());
        return ResponseEntity.ok()
            .contentLength(file.contentLength())
            .contentType(
                MediaType.parseMediaType(Files.probeContentType(Paths.get(personDTO.getImgPath()))))
            .body(new InputStreamResource(file.getInputStream()));
      }catch (Exception e){
        log.warn(e.getMessage());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_IMAGE_MESSAGE);
      }
    }
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_MESSAGE_SESSION);
  }

}
