package com.example.androidQr.service;

import com.example.androidQr.dto.EventDTO;
import com.example.androidQr.mapper.EventMapper;
import com.example.androidQr.repository.EventRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EventService {

  private final EventRepository eventRepository;

  public EventDTO getEvent(Integer id) throws Exception {
    EventDTO eventDTO = EventMapper.mapToDTO(eventRepository.findById(id)
        .orElseThrow(() -> new Exception("Event с id=" + id + " не найден!")));

    return eventDTO;
  }

  public List<EventDTO> getAll(){
    List<EventDTO> result = eventRepository.getAll().stream()
        .map(EventMapper::mapToDTO)
        .collect(Collectors.toList());

    return result;
  }

}
