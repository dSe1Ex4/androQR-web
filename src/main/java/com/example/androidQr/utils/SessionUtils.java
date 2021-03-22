package com.example.androidQr.utils;

import com.example.androidQr.entity.User;
import com.example.androidQr.repository.UserRepository;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SessionUtils {

  private final UserRepository userRepository;

  /**
   * Метод для генерации сессии
   *
   * @param ip ip пользователя
   * @return сгенерированная сессия
   */
  public static String generateSession(String secretKey, String ip, int id) {

    String session = Hashing.sha256()
        .hashString(secretKey + ip, StandardCharsets.UTF_8).toString();

    return id + "." + session;
  }


  public boolean verifySession(String sessionId) throws Exception {
    // проверка сессии
    User user = userRepository.findById(Integer.valueOf(sessionId.split("\\.")[0]))
        .orElseThrow(() -> new Exception(
            "User with id=" + Integer.valueOf(sessionId.split(".")[0]) + " was not found."));

    HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();

    return SessionUtils.generateSession(user.getSecretKey(), httpServletRequest.getRemoteAddr(), user.getId())
        .equals(sessionId);


  }

  public static String generateSecretKey() {
    SecureRandom secureRandom = new SecureRandom();
    byte bytes[] = new byte[20];
    secureRandom.nextBytes(bytes);
    return Hashing.sha256().hashBytes(bytes).toString();
  }

}
