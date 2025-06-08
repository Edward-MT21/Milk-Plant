package com.persons.Persons.services;

import com.persons.Persons.model.dtos.PersonInDto;
import com.persons.Persons.model.dtos.PersonOutDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IPersonService {
    void createPerson(PersonInDto personInDto);

    void editPerson(PersonInDto personInDto);

    List<PersonOutDto> getAllPersons();
}
