package com.persons.Persons.controllers;

import com.module.Common.dtos.PersonOutDto;
import com.module.Common.dtos.ResponseDto;
import com.persons.Persons.constants.PersonConstants;
import com.persons.Persons.model.dtos.PersonInDto;
import com.persons.Persons.model.dtos.PersonsContactsDto;
import com.persons.Persons.services.IPersonService;
import com.persons.Persons.services.client.IMilkCollectionFeignClient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PersonController")
@AllArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    IPersonService iPersonService;

    @Autowired
    private PersonsContactsDto personsContactsDto;


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
        iPersonService.createPerson(personInDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(PersonConstants.STATUS_201, PersonConstants.MESSAGE_201));
    }

    /**
     * Edits a person with the given data.
     * @param personInDto the data transfer object containing the details of the person to be edited
     *                    including names, last names, identification number, age, and gender.
     */
    @PutMapping("/editPerson")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDto> editPerson(@Valid @RequestBody PersonInDto personInDto) {
        iPersonService.editPerson(personInDto);
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
        List<PersonOutDto> listPersonOutDto = iPersonService.getAllPersons();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listPersonOutDto);
    }

    @GetMapping("/getPersonsContacts")
    public ResponseEntity<PersonsContactsDto> getPersonsContacts() {
        logger.debug("Invoking getPersonsContacts");
        return ResponseEntity.status(HttpStatus.OK).body(personsContactsDto);
    }

    @GetMapping("/fetchPersonById")
    public ResponseEntity<PersonOutDto> fetchPersonById(
            @RequestHeader("milk-plant-correlation-id") String correlationId, @RequestParam("personId") Long personId) {
        logger.debug("fetchPersonById start");
        PersonOutDto personOutDto = iPersonService.fetchPersonById(personId);
        logger.debug("fetchPersonById end");
        return ResponseEntity.status(HttpStatus.OK).body(personOutDto);
    }

    @DeleteMapping("/deletePersonById")
    public ResponseEntity<ResponseDto> deletePersonById(@RequestParam("personId") Long personId) {


        boolean isDeleted = iPersonService.deletePersonById(personId);

        if (!isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto("417", "Person not deleted (local deletion failed)"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto("200", "Person deleted successfully"));

    }


}
