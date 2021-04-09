package com.example.androidQr.service;

import com.example.androidQr.dto.EventRoleDTO;
import com.example.androidQr.dto.MapParamDTOValueDTO;
import com.example.androidQr.dto.ParamDTO;
import com.example.androidQr.dto.PersonDTO;
import com.example.androidQr.dto.RoleDTO;
import com.example.androidQr.dto.ValueDTO;
import com.example.androidQr.entity.Event;
import com.example.androidQr.entity.EventRole;
import com.example.androidQr.entity.Measure;
import com.example.androidQr.entity.Person;
import com.example.androidQr.entity.Role;
import com.example.androidQr.entity.Value;
import com.example.androidQr.mapper.EventMapper;
import com.example.androidQr.mapper.EventRoleMapper;
import com.example.androidQr.mapper.MeasureMapper;
import com.example.androidQr.mapper.ParamMapper;
import com.example.androidQr.mapper.PersonMapper;
import com.example.androidQr.mapper.RoleMapper;
import com.example.androidQr.mapper.ValueMapper;
import com.example.androidQr.repository.EventRepository;
import com.example.androidQr.repository.EventRoleRepository;
import com.example.androidQr.repository.MeasureRepository;
import com.example.androidQr.repository.ParamRepository;
import com.example.androidQr.repository.PersonRepository;
import com.example.androidQr.repository.RoleRepository;
import com.example.androidQr.repository.ValueRepository;
import com.example.androidQr.utils.ImageUtils;
import com.example.androidQr.utils.QRCodeGeneratorUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RegistrationService {

  private final ImageUtils imageUtils;

  private final QRCodeGeneratorUtils qrCodeGeneratorUtils;
  private final PersonRepository personRepository;
  private final EventRoleRepository eventRoleRepository;
  private final MeasureRepository measureRepository;
  private final ValueRepository valueRepository;
  private final RoleRepository roleRepository;
  private final EventRepository eventRepository;
  private final ParamRepository paramRepository;

  public String registerPerson(PersonDTO personDTO, String eventId, String roleId,
      MapParamDTOValueDTO mapParamDTOValueDTO, MultipartFile file) throws Exception {
    String uuid = UUID.randomUUID().toString();
    personDTO.setImgPath(imageUtils.savePersonImage(file, uuid));

    // сохраняем человека
    Person person = PersonMapper.mapToEntity(personDTO);
//    personRepository.save());

    // сохраняем eventrole
    Role role = roleRepository.findById(Integer.valueOf(roleId))
        .orElseThrow(()->new Exception("New Exception 51 line"));

    Event event = eventRepository.findById(Integer.valueOf(eventId))
        .orElseThrow(()->new Exception("New Exception 55 line"));

    EventRole eventRole = new EventRole();
    eventRole.setRole(role);
    eventRole.setEvent(event);

    //eventRoleRepository.save(eventRole);

    EventRoleDTO eventRoleDTO = new EventRoleDTO();
    eventRoleDTO.setEventDTO(EventMapper.mapToDTO(event));
    eventRoleDTO.setRoleDTO(RoleMapper.mapToDTO(role));

//    Measure measure = MeasureMapper
//        .mapToEntity(personDTO, eventRoleDTO, uuid);
    Measure measure = new Measure();
    measure.setPerson(PersonMapper.mapToEntity(personDTO));
    measure.setUuid(uuid);
    measure.setEventRole(eventRole);

    measureRepository.save(measure);

    // переделать под стреам
    Map<ParamDTO, ValueDTO> paramDTOValueDTOMap = new HashMap<>();

    for (Map.Entry<String, String> entry : mapParamDTOValueDTO.getParamDTOValueDTOMap().entrySet()){
      ParamDTO paramDTO = ParamMapper.mapToDTO(paramRepository.findByName(entry.getKey()));
      Value value = new Value();
      value.setValue(entry.getValue());
      value.setParam(paramRepository.findByName(entry.getKey()));
      value.setMeasure(measure);

      valueRepository.save(value);
    }

//    paramDTOValueDTOMap.entrySet().stream()
//        .map(Map.Entry::getValue)
//        .forEach(s -> {
//          Value value = ValueMapper.mapToEntity(s);
//          value.setMeasure(measure);
//          valueRepository.save(value);
//        });

    String path = qrCodeGeneratorUtils.generatedQRCodeImage(uuid, uuid);

    return path;
  }

}
