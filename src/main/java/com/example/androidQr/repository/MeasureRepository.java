package com.example.androidQr.repository;

import com.example.androidQr.entity.Measure;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Integer> {

  Optional<Measure> findByUuid(String uuid);

}
