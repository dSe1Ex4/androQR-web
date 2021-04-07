package com.example.androidQr.service;

import static com.example.androidQr.utils.SessionUtils.generateSecretKey;
import static com.example.androidQr.utils.SessionUtils.generateSession;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.androidQr.entity.User;
import com.example.androidQr.repository.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthorizationService {

  private final UserRepository userRepository;

  public Map<String, String> authorization(String login, String password) throws Exception {
    User user = userRepository.findByLogin(login)
        .orElse(null);
    if(user == null){
      return null;
    }

    if (BCrypt.verifyer()
        .verify(password.toCharArray(), user.getPasswordHash().toCharArray()).verified) {

      Map<String, String> result = new HashMap<>();
      String secretKey = generateSecretKey();

      user.setSecretKey(secretKey);
      user.setDataExpireKey(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
      userRepository.save(user);


      HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
          .getRequestAttributes()).getRequest();
      String session = generateSession(secretKey, httpServletRequest.getRemoteAddr(), user.getId());
      result.put("session_id", session);

      return result;
    }

    return null;
  }

}
