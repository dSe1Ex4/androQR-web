package com.example.androidQr.service;

import com.example.androidQr.dto.EventDTO;
import com.example.androidQr.dto.MapParamDTOValueDTO;
import com.example.androidQr.dto.PersonDTO;
import com.example.androidQr.dto.RoleDTO;
import com.example.androidQr.entity.Measure;
import com.example.androidQr.entity.Person;
import com.example.androidQr.entity.Value;
import com.example.androidQr.mapper.EventRoleMapper;
import com.example.androidQr.mapper.MeasureMapper;
import com.example.androidQr.mapper.PersonMapper;
import com.example.androidQr.mapper.ValueMapper;
import com.example.androidQr.repository.EventRoleRepository;
import com.example.androidQr.repository.MeasureRepository;
import com.example.androidQr.repository.PersonRepository;
import com.example.androidQr.repository.ValueRepository;
import com.example.androidQr.utils.QRCodeGeneratorUtils;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RegistrationService {

  private final QRCodeGeneratorUtils qrCodeGeneratorUtils;
  private final PersonRepository personRepository;
  private final EventRoleRepository eventRoleRepository;
  private final MeasureRepository measureRepository;
  private final ValueRepository valueRepository;

  public String registerPerson(PersonDTO personDTO, EventDTO eventDTO, RoleDTO roleDTO,
      MapParamDTOValueDTO mapParamDTOValueDTO) throws IOException, WriterException {

//    // сохраняем человека
//    personRepository.save(PersonMapper.mapToEntity(personDTO));
//    // сохраняем eventrole
//    eventRoleRepository.save(EventRoleMapper.mapToEntity(roleDTO, eventDTO));
//    // сохраняем value

    String uuid = UUID.randomUUID().toString();
    Measure measure = MeasureMapper
        .mapToEntity(personDTO, EventRoleMapper.mapToDTO(roleDTO, eventDTO), uuid);
    measureRepository.save(measure);

    mapParamDTOValueDTO.getMap().entrySet().stream()
        .map(Map.Entry::getValue)
        .forEach(s -> {
          Value value = ValueMapper.mapToEntity(s);
          value.setMeasure(measure);
          valueRepository.save(value);
        });

    String path = qrCodeGeneratorUtils.generatedQRCodeImage(UUID.randomUUID().toString(),
        personDTO.getFirstName() + personDTO.getSecondName() + personDTO.getThreeName());

    return path;
  }

}
