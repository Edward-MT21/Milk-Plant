package com.persons.Persons.services;

import com.persons.Persons.model.dtos.PersonInDto;
import com.persons.Persons.model.dtos.PersonOutDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IPersonService {

    void createPerson(PersonInDto personInDto);

    void editPerson(PersonInDto personInDto);

    List<PersonOutDto> getAllPersons();

    PersonOutDto fetchPersonById(Long personId);

    boolean deletePersonById(Long personId);
}
