package com.persons.Persons.controllers;

import com.persons.Persons.constants.PersonConstants;
import com.persons.Persons.model.dtos.PersonInDto;
import com.persons.Persons.model.dtos.PersonOutDto;
import com.persons.Persons.model.dtos.ResponseDto;
import com.persons.Persons.services.IPersonService;
import com.persons.Persons.services.impl.PersonServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PersonController")
@AllArgsConstructor
@Validated
public class PersonController {

    PersonServiceImpl personServiceImpl;

//    public PersonController(IPersonService personServiceImpl) {
//        this.personServiceImpl = (PersonServiceImpl) personServiceImpl;
//    }

    @GetMapping("/getGreeting")
    public String getGreeting() {
        return "Hello World since getGreeting";
    }

    /**
     * Creates a new person with the given data.
     * @param personInDto the data transfer object containing the details of the person to be created.
     */
    @PostMapping("/createPerson")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> createPerson(@Valid @RequestBody PersonInDto personInDto) {
        personServiceImpl.createPerson(personInDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(PersonConstants.STATUS_201, PersonConstants.MESSAGE_201));
    }

    /**
     * Edits a person with the given data.
     * @param personInDto the data transfer object containing the details of the person to be edited
     *                    including names, last names, identification number, age, and gender.
     */
    @PostMapping("/editPerson")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDto> editPerson(@Valid @RequestBody PersonInDto personInDto) {
        personServiceImpl.editPerson(personInDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(PersonConstants.STATUS_200, PersonConstants.MESSAGE_200));
    }

    /**
     * Returns a list of all persons in the database.
     * @return a list of data transfer objects containing the details of each person
     */
    @GetMapping("/getAllPersons")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PersonOutDto>> getAllPersons() {
        List<PersonOutDto> listPersonOutDto = personServiceImpl.getAllPersons();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listPersonOutDto);
    }
}
