package com.example.androidQr.dto;

import com.example.androidQr.dto.enums.Event;
import com.example.androidQr.dto.enums.Gun;
import com.example.androidQr.dto.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private String familyName;
  private String firstName;
  private String middleName;
  private Gun gun;
  private Role role;
  private Event event;
  private String fileName;

}
