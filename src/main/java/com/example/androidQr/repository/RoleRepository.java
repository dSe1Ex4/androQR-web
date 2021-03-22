package com.example.androidQr.repository;

import com.example.androidQr.entity.Event;
import com.example.androidQr.entity.EventRole;
import com.example.androidQr.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  // todo тут нада потом посмотреть
  @Query("SELECT er.role FROM EventRole er WHERE er.event.id = 1")
  List<Role> getRole(Event event);

}
