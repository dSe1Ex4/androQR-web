package com.example.androidQr.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class MapParamDTOValueDTO {

  public Map<String, String> paramDTOValueDTOMap = new HashMap<>();

  public Map<String, String> getParamDTOValueDTOMap() {
    return paramDTOValueDTOMap;
  }

  public void setParamDTOValueDTOMap(Map<String, String> paramDTOValueDTOMap) {
    this.paramDTOValueDTOMap = paramDTOValueDTOMap;
  }

  //  public Map<String, String> getMap() {
//    if (paramDTOValueDTOMap == null) {
//      paramDTOValueDTOMap = new HashMap<String, String>();
//    }
//    return paramDTOValueDTOMap;
//  }

}
