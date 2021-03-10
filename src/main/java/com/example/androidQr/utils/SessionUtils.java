package com.example.androidQr.utils;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class SessionUtils {

  /**
   * Метод для генерации сессии
   *
   * @param login логин пользователя
   * @return сгенерированная сессия
   */
  public static String generateSession(String secretKey, String login) {

    String session = Hashing.sha256()
        .hashString(secretKey + login, StandardCharsets.UTF_8).toString();

    return session;
  }

  public static String generateSecretKey() {
    SecureRandom secureRandom = new SecureRandom();
    byte bytes[] = new byte[20];
    secureRandom.nextBytes(bytes);
    return Hashing.sha256().hashBytes(bytes).toString();
  }

}
