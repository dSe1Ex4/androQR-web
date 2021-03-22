package com.example.androidQr.mapper;

import com.example.androidQr.dto.PersonDTO;
import com.example.androidQr.entity.Person;

public class PersonMapper {

  public static Person mapToEntity(PersonDTO personDTO){
    Person person = new Person();
    person.setFirstName(personDTO.getFirstName());
    person.setSecondName(personDTO.getSecondName());
    person.setThreeName(personDTO.getThreeName());
    person.setImgPath(personDTO.getImgPath());

    return person;
  }

  public static PersonDTO mapToDTO(Person person){
    PersonDTO personDTO = new PersonDTO();
    personDTO.setFirstName(person.getFirstName());
    personDTO.setSecondName(person.getSecondName());
    personDTO.setThreeName(person.getThreeName());
    personDTO.setImgPath(person.getImgPath());

    return personDTO;
  }

}
