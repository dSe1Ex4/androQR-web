package com.example.androidQr.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private String firstName;
  private String name;
  private String secondName;
  private Gun gun;
  private Role role;
  private Event event;

}
