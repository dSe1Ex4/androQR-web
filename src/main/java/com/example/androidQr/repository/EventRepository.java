package com.example.androidQr.repository;

import com.example.androidQr.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

  @Query("SELECT e FROM Event e")
  List<Event> getAll();

}
