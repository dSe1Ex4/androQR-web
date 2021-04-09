package com.example.androidQr.repository;

import com.example.androidQr.entity.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamRepository extends JpaRepository<Param, Integer> {

  List<Param> findAllByEventList_name(String eventName);

  Param findByName(String name);

}
