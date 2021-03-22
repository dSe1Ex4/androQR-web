package com.example.androidQr.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.example.androidQr.generator")
public class RestCookieCheckController {

  @GetMapping("/all-cookies")
  public String readAllCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      return Arrays.stream(cookies)
          .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
    }
    return "No cookies";
  }
}
