package com.example.androidQr.repository;

import com.example.androidQr.entity.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

  Optional<Person> findById(int id);

}
