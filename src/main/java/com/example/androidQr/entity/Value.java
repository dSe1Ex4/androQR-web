package com.example.androidQr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Value")
public class Value {

  @EmbeddedId
  private ParamMeasureId paramMeasureId = new ParamMeasureId();

  @ManyToOne
  @MapsId("idParam")
  @JoinColumn(name = "id_param")
  private Param param;

  @ManyToOne
  @MapsId("idMeasure")
  @JoinColumn(name = "id_measure")
  private Measure measure;

  @Column(name = "value")
  private String value;

}
