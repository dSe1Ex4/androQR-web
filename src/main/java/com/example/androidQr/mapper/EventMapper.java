package com.example.androidQr.mapper;

import com.example.androidQr.dto.EventDTO;
import com.example.androidQr.entity.Event;

public class EventMapper {

  public static EventDTO mapToDTO(Event event){
    EventDTO eventDTO = new EventDTO();
    eventDTO.setName(event.getName());
    eventDTO.setDateStart(eventDTO.getDateStart());
    eventDTO.setDateEnd(eventDTO.getDateEnd());
    eventDTO.setLimit(event.getLimit());
    eventDTO.setAddress(event.getAddress());

    return eventDTO;
  }

  public static Event mapToEntity(EventDTO eventDTO){
    Event event = new Event();
    event.setAddress(eventDTO.getAddress());
    event.setDateEnd(eventDTO.getDateEnd());
    event.setDateStart(eventDTO.getDateStart());
    event.setLimit(eventDTO.getLimit());
    event.setName(eventDTO.getName());

    return event;
  }


}
