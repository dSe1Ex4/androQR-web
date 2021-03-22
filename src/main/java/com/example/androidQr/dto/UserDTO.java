package com.example.androidQr.dto;

import lombok.Data;

@Data
public class UserDTO {

  private String login;
  private String passwordHash;
  private String secretKey;
  private String dataExpireKey;

}
