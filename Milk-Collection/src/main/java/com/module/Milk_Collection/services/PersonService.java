package com.module.Milk_Collection.services;

import com.module.Milk_Collection.model.dtos.PersonRequest;
import com.module.Milk_Collection.model.dtos.PersonResponse;
import com.module.Milk_Collection.model.entities.Person;
import com.module.Milk_Collection.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public void addPerson(PersonRequest personRequest) {

        var person = Person.builder().
                names(personRequest.getNames()).
                lastNames(personRequest.getLastNames()).
                identificationNumber(personRequest.getIdentificationNumber()).
                age(personRequest.getAge()).
                gender(personRequest.getGender())
                .build();

        personRepository.save(person);

        log.info("Person added: {}", person);

    }

    public List<PersonResponse> getAllPersons() {

        var persons = personRepository.findAll();
        return persons.stream().map(this::mapPersonResponse).toList();
    }

    private PersonResponse mapPersonResponse(Person person) {
        return PersonResponse.builder().
                idPerson(person.getIdPerson()).
                names(person.getNames()).
                lastNames(person.getLastNames()).
                identificationNumber(person.getIdentificationNumber()).
                age(person.getAge()).
                gender(person.getGender())
                .build();
    }

}
