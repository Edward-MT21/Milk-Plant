package com.persons.Persons.services;

import com.module.Common.dtos.PersonOutDto;
import com.persons.Persons.model.dtos.PersonInDto;

import java.util.List;

public interface IPersonService {

    void createPerson(PersonInDto personInDto);

    void editPerson(PersonInDto personInDto);

    List<PersonOutDto> getAllPersons();

    PersonOutDto fetchPersonById(Long personId);

    boolean deletePersonById(Long personId);
}
