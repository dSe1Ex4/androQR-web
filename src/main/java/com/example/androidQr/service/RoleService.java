package com.example.androidQr.service;

import com.example.androidQr.dto.EventDTO;
import com.example.androidQr.dto.RoleDTO;
import com.example.androidQr.mapper.EventMapper;
import com.example.androidQr.mapper.RoleMapper;
import com.example.androidQr.repository.RoleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleService {

  private final RoleRepository roleRepository;

  public List<RoleDTO> getRole(EventDTO eventDTO) {
    return roleRepository.getRole(EventMapper.mapToEntity(eventDTO)).stream()
        .map(RoleMapper::mapToDTO)
        .distinct()
        .collect(Collectors.toList());
  }


}
