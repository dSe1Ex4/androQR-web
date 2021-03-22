package com.example.androidQr.mapper;


import com.example.androidQr.dto.EventRoleDTO;
import com.example.androidQr.dto.MeasureDTO;
import com.example.androidQr.dto.PersonDTO;
import com.example.androidQr.entity.Measure;

public class MeasureMapper {

  public static MeasureDTO mapToDTO(Measure measure) {
    MeasureDTO measureDTO = new MeasureDTO();
    measureDTO.setPersonDTO(PersonMapper.mapToDTO(measure.getPerson()));
    measureDTO.setEventRoleDTO(EventRoleMapper.mapToDTO(measure.getEventRole()));
    measureDTO.setUuid(measure.getUuid());

    return measureDTO;
  }

  public static Measure mapToEntity(PersonDTO personDTO, EventRoleDTO eventRoleDTO, String uuid) {
    Measure measure = new Measure();
    measure.setUuid(uuid);
    measure.setEventRole(EventRoleMapper.mapToEntity(eventRoleDTO));
    measure.setPerson(PersonMapper.mapToEntity(personDTO));

    return measure;
  }

  public static Measure mapToEntity(MeasureDTO measureDTO) {
    Measure measure = new Measure();
    measure.setUuid(measureDTO.getUuid());
    measure.setEventRole(EventRoleMapper.mapToEntity(measureDTO.getEventRoleDTO()));
    measure.setPerson(PersonMapper.mapToEntity(measureDTO.getPersonDTO()));

    return measure;
  }

}
