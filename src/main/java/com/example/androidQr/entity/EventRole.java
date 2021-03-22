package com.example.androidQr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "EventRole")
public class EventRole {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "id_role")
  private Role role;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_event")
  private Event event;


}
