package com.module.Milk_Collection.services;

import com.module.Milk_Collection.model.dtos.PersonInDto;
import com.module.Milk_Collection.model.dtos.PersonOutDto;
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

    public void addPerson(PersonInDto personInDto) {

        var person = Person.builder().
                names(personInDto.getNames()).
                lastNames(personInDto.getLastNames()).
                identificationNumber(personInDto.getIdentificationNumber()).
                age(personInDto.getAge()).
                gender(personInDto.getGender())
                .build();

        personRepository.save(person);

        log.info("Person added: {}", person);

    }

    public List<PersonOutDto> getAllPersons() {

        var persons = personRepository.findAll();
        return persons.stream().map(this::mapPersonResponse).toList();
    }

    private PersonOutDto mapPersonResponse(Person person) {
        return PersonOutDto.builder().
                idPerson(person.getPersonId()).
                names(person.getNames()).
                lastNames(person.getLastNames()).
                identificationNumber(person.getIdentificationNumber()).
                age(person.getAge()).
                gender(person.getGender())
                .build();
    }

}
