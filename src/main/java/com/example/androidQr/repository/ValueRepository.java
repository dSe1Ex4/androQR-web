package com.example.androidQr.repository;

import com.example.androidQr.entity.Measure;
import com.example.androidQr.entity.ParamMeasureId;
import com.example.androidQr.entity.Value;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueRepository extends JpaRepository<Value, ParamMeasureId> {

  // List<Value> findByParamMeasureId_IdMeasure(int idMeasure);
  List<Value> findByMeasure(Measure measure);

}
