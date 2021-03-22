package com.example.androidQr.mapper;

import com.example.androidQr.dto.MapPersonDTO;
import com.example.androidQr.entity.Measure;
import com.example.androidQr.entity.Value;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapPersonDTOMapper {

  public static MapPersonDTO mapToDTO(Measure measure,
      List<Value> values) {

    MapPersonDTO mapPersonDTO = new MapPersonDTO();
    mapPersonDTO.getMap().put("eventname", measure.getEventRole().getEvent().getName());
    mapPersonDTO.getMap().put("role", measure.getEventRole().getRole().getName());
    mapPersonDTO.getMap().put("firstname", measure.getPerson().getFirstName());
    mapPersonDTO.getMap().put("secondname", measure.getPerson().getSecondName());
    mapPersonDTO.getMap().put("threename", measure.getPerson().getThreeName());

    Map<String, String> extra = new HashMap<>();
    values.forEach(s -> {
      extra.put(s.getParam().getName(), s.getValue());
    });
    mapPersonDTO.getMap().put("extra", extra);

    return mapPersonDTO;
  }


}
