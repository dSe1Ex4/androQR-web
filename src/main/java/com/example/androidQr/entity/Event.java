package com.example.androidQr.entity;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Event")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "date_start")
  private Timestamp dateStart;

  @Column(name = "date_end")
  private Timestamp dateEnd;

  @Column(name = "limit")
  private int limit;

  @Column(name = "address")
  private String address;

  @ManyToMany
  @JoinTable(
      name = "EventParam",
      joinColumns = @JoinColumn(name = "id_event"),
      inverseJoinColumns = @JoinColumn(name = "id_param")
  )
  private List<Param> paramList;

  @ManyToOne
  @JoinColumn(name = "id_even_holder")
  private EventHolder eventHolder;

}
