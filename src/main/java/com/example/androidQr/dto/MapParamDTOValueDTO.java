package com.example.androidQr.dto;

import java.util.HashMap;
import java.util.Map;


public class MapParamDTOValueDTO {

  private Map<ParamDTO, ValueDTO> paramDTOValueDTOMap;


  public Map<ParamDTO, ValueDTO> getMap() {
    if (paramDTOValueDTOMap == null) {
      paramDTOValueDTOMap = new HashMap<ParamDTO, ValueDTO>();
    }
    return paramDTOValueDTOMap;
  }

}
