package com.persons.Persons.services.impl;

import com.module.Common.dtos.PersonOutDto;
import com.module.Common.dtos.ResponseDto;
import com.persons.Persons.exception.ResourceNotFoundException;
import com.persons.Persons.model.dtos.PersonInDto;
import com.persons.Persons.model.entities.Person;
import com.persons.Persons.repositories.IPersonRepository;
import com.persons.Persons.services.IPersonService;
import com.persons.Persons.services.client.IMilkCollectionFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@Slf4j
public class PersonServiceImpl implements IPersonService {

    IPersonRepository iPersonRepository;
    IMilkCollectionFeignClient iMilkCollectionFeignClient;

    public PersonServiceImpl(IPersonRepository iPersonRepository, IMilkCollectionFeignClient iMilkCollectionFeignClient) {

        this.iPersonRepository = iPersonRepository;
        this.iMilkCollectionFeignClient = iMilkCollectionFeignClient;
    }

    @Override
    public void createPerson(PersonInDto personInDto) {
        Person person = new Person();
        person.setNames(personInDto.getNames());
        person.setLastNames(personInDto.getLastNames());
        person.setIdentificationNumber(personInDto.getIdentificationNumber());
        person.setBirthdate(personInDto.getBirthdate());
        person.setGender(personInDto.getGender());
        person.setEmail(personInDto.getEmail());
        person.setMobileNumber(personInDto.getMobileNumber());

        iPersonRepository.save(person);

        log.info("Person added: {}", person);
    }

    /**
     * Edits a person with the given data.
     * @param personInDto the data transfer object containing the details of the person to be edited.
     */
    public void editPerson( PersonInDto personInDto) {
        iPersonRepository.findById(personInDto.getIdPerson()).orElseThrow(() -> new RuntimeException("Person not found"));
        var person = Person.builder().
                personId(personInDto.getIdPerson()).
                names(personInDto.getNames()).
                lastNames(personInDto.getLastNames()).
                identificationNumber(personInDto.getIdentificationNumber()).
                birthdate(personInDto.getBirthdate()).
                gender(personInDto.getGender()).
                email(personInDto.getEmail()).
                mobileNumber(personInDto.getMobileNumber())
                .build();
        iPersonRepository.save(person);
    }

    /**
     * Returns a list of all persons in the database.
     * @return a list of data transfer objects containing the details of each person
     */
    public List<PersonOutDto> getAllPersons() {
        return iPersonRepository.findAll().stream().map(this::mapPersonToPersonOutDto).toList();
    }

    private PersonOutDto mapPersonToPersonOutDto(Person person) {
        return PersonOutDto.builder().
                idPerson(person.getPersonId()).
                names(person.getNames()).
                lastNames(person.getLastNames()).
                identificationNumber(person.getIdentificationNumber()).
                birthdate(person.getBirthdate()).
                gender(person.getGender()).
                email(person.getEmail()).
                mobileNumber(person.getMobileNumber())
                .build();
    }

    public PersonOutDto fetchPersonById(Long personId) {
        Person person = iPersonRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("Person", "personId", personId.toString())
        );

        return mapPersonToPersonOutDto(person);
    }

    public boolean deletePersonById(Long personId) {

        iPersonRepository.findById(personId).orElseThrow(() -> new RuntimeException("Person not found"));

        ResponseEntity<ResponseDto> feignResponse = iMilkCollectionFeignClient.deleteMilkSupplierByPersonId(personId);

        boolean feignSuccess = feignResponse.getStatusCode() == HttpStatus.OK &&
                "200".equals(feignResponse.getBody().getStatusCode());

        if (!feignSuccess) {
            return false;
        }

        iPersonRepository.deleteById(personId);
        return true;
    }


}
