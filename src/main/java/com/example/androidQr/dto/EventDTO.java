package com.example.androidQr.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class EventDTO {

  private String name;
  private Timestamp dateStart;
  private Timestamp dateEnd;
  private int limit;
  private String address;

}
