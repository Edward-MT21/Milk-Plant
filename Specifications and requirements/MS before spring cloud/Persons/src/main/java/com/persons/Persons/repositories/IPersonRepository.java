package com.persons.Persons.repositories;

import com.persons.Persons.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Long> {
}
