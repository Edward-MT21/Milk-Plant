package com.module.Milk_Collection.controllers;

import com.module.Milk_Collection.model.dtos.PersonRequest;
import com.module.Milk_Collection.model.dtos.PersonResponse;
import com.module.Milk_Collection.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PersonController")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody PersonRequest personRequest) {
        personService.addPerson(personRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResponse> getAllPersons() {
        return personService.getAllPersons();
    }

}
