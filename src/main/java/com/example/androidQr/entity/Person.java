package com.example.androidQr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @Column(name = "firstname")
  private String firstName;

  @Column(name = "secondname")
  private String secondName;

  @Column(name = "threename")
  private String threeName;

  @Column(name = "img_path")
  private String imgPath;

}
