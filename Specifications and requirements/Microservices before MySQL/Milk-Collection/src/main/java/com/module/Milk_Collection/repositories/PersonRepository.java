package com.module.Milk_Collection.repositories;

import com.module.Milk_Collection.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
