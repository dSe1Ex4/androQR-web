package com.example.androidQr.repository;

import com.example.androidQr.entity.EventRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRoleRepository extends JpaRepository<EventRole, Integer> {

}
