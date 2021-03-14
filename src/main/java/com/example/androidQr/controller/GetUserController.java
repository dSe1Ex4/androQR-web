package com.example.androidQr.controller;

import com.example.androidQr.dto.RoleDTO;
import com.example.androidQr.dto.UserDTO;
import com.example.androidQr.dto.WeaponDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetUserController {

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam(value = "uuid") String uuid) {

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Валерий");
        userDTO.setLastName("Жмышенко");
        userDTO.setMiddleName("Альбертович");
        userDTO.setEvent("Поточек");

        WeaponDTO weaponDTO = new WeaponDTO();
        weaponDTO.setName("Пистолет");

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(10);

        Map<String, Object> extra = new HashMap<>();
        extra.put("weapon", weaponDTO.getName());
        extra.put("roleId", String.valueOf(roleDTO.getId()));

        Map<String, Object> result = new HashMap<>();
        result.put("user", userDTO);
        result.put("extra", extra);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);


    }

}
