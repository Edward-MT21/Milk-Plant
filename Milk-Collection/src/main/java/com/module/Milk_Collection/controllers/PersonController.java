package com.module.Milk_Collection.controllers;

import com.module.Milk_Collection.model.dtos.PersonRequest;
import com.module.Milk_Collection.model.dtos.PersonResponse;
import com.module.Milk_Collection.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PersonController")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addPerson(@RequestBody PersonRequest personRequest) {
        personService.addPerson(personRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public List<PersonResponse> getAllPersons() {
        return personService.getAllPersons();
    }

}
