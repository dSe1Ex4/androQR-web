package com.example.androidQr.dto;

import java.util.HashMap;
import java.util.Map;

public class MapPersonDTO {

  private Map<String, Object> map;

  public Map<String, Object> getMap() {
    if (map == null) {
      map = new HashMap<>();
    }
    return map;
  }

}
