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
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PersonService {

  private final SessionUtils sessionUtils;
  private final ValueRepository valueRepository;
  private final MeasureRepository measureRepository;
  private final PersonRepository personRepository;


  public Map<String, Object> getMeasurePersonInfo(String sessionId, String uuid) throws Exception {

    if (sessionId != null && uuid != null && sessionUtils.verifySession(sessionId)) {
      Measure measure = measureRepository.findByUuid(uuid)
          .orElseThrow(() -> new Exception("Measure with uuid=" + uuid + " was not found."));
      List<Value> values = valueRepository.findByMeasure(measure);

      return MapPersonDTOMapper.mapToDTO(measure, values).getMap();
    }

    return null;
  }

  public PersonDTO getPersonImage(String sessionId, String uuid) throws Exception {
    if (sessionId != null && uuid != null && sessionUtils.verifySession(sessionId)) {
      Measure measure = measureRepository.findByUuid(uuid)
          .orElseThrow(() -> new Exception("Measure with uuid=" + uuid + " was not found."));

      return PersonMapper
          .mapToDTO(personRepository.findById(measure.getPerson().getId()).orElseThrow(
              () -> new Exception(
                  "Person with id=" + measure.getPerson().getId() + " was not found")));


    }

    return null;
  }

}
