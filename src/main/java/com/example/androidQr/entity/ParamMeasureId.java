package com.example.androidQr.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ParamMeasureId implements Serializable {

  @Column(name = "id_param")
  private int idParam;

  @Column(name = "id_measure")
  private int idMeasure;

}
