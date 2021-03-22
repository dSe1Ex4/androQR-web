package com.example.androidQr.service;

import com.example.androidQr.dto.EventDTO;
import com.example.androidQr.dto.ParamDTO;
import com.example.androidQr.entity.Param;
import com.example.androidQr.mapper.EventMapper;
import com.example.androidQr.mapper.ParamMapper;
import com.example.androidQr.repository.ParamRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ParamService {

  private final ParamRepository paramRepository;

  public List<ParamDTO> getParam(EventDTO eventDTO) {

    return paramRepository.findAllByEventList_name(EventMapper.mapToEntity(eventDTO).getName()).stream()
        .map(ParamMapper::mapToDTO)
        .collect(Collectors.toList());
  }

}
