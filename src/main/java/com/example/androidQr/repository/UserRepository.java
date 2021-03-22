package com.example.androidQr.repository;

import com.example.androidQr.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findById(int id);
  Optional<User> findByLogin(String login);
}
