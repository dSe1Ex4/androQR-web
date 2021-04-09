package com.example.androidQr.mapper;

import com.example.androidQr.dto.EventDTO;
import com.example.androidQr.dto.EventRoleDTO;
import com.example.androidQr.dto.RoleDTO;
import com.example.androidQr.entity.Event;
import com.example.androidQr.entity.EventRole;
import com.example.androidQr.entity.Role;

public class EventRoleMapper {

  public static EventRole mapToEntity(EventRoleDTO eventRoleDTO){
    EventRole eventRole = new EventRole();
    eventRole.setEvent(EventMapper.mapToEntity(eventRoleDTO.getEventDTO()));
    eventRole.setRole(RoleMapper.mapToEntity(eventRoleDTO.getRoleDTO()));

    return eventRole;
  }

  public static EventRole mapToEntity(RoleDTO roleDTO, EventDTO eventDTO){
    EventRole eventRole = new EventRole();
    eventRole.setEvent(EventMapper.mapToEntity(eventDTO));
    eventRole.setRole(RoleMapper.mapToEntity(roleDTO));

    return eventRole;
  }

  public static EventRoleDTO mapToDTO(RoleDTO roleDTO, EventDTO eventDTO){
    EventRoleDTO eventRoleDTO = new EventRoleDTO();
    eventRoleDTO.setRoleDTO(roleDTO);
    eventRoleDTO.setEventDTO(eventDTO);

    return eventRoleDTO;
  }

  public static EventRoleDTO mapToDTO(EventRole eventRole){
    EventRoleDTO eventRoleDTO = new EventRoleDTO();
    eventRoleDTO.setRoleDTO(RoleMapper.mapToDTO(eventRole.getRole()));
    eventRoleDTO.setEventDTO(EventMapper.mapToDTO(eventRole.getEvent()));

    return eventRoleDTO;
  }

  public static EventRoleDTO mapToDTO(Role role, Event event){
    EventRoleDTO eventRoleDTO = new EventRoleDTO();
    eventRoleDTO.setRoleDTO(RoleMapper.mapToDTO(role));
    eventRoleDTO.setEventDTO(EventMapper.mapToDTO(event));

    return eventRoleDTO;
  }

}
