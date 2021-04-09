package com.example.androidQr.mapper;

import com.example.androidQr.dto.RoleDTO;
import com.example.androidQr.entity.Role;

public class RoleMapper {

  public static RoleDTO mapToDTO(Role role){
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setId(role.getId());
    roleDTO.setName(role.getName());

    return roleDTO;
  }

  public static Role mapToEntity(RoleDTO roleDTO){
    Role role = new Role();
    role.setName(roleDTO.getName());

    return role;
  }


}
