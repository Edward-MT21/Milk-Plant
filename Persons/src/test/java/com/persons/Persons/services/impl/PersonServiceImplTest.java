package com.persons.Persons.services.impl;

import com.persons.Persons.model.dtos.PersonInDto;
import com.persons.Persons.model.entities.Person;
import com.persons.Persons.repositories.IPersonRepository;
import com.persons.Persons.services.IPersonService;
import com.persons.Persons.services.client.IMilkCollectionFeignClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(PersonServiceImpl.class)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Desactiva H2
public class PersonServiceImplTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Asigna la URL y credenciales del contenedor dinámico a Spring
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

    }

    @Autowired
    private IPersonService personService;

    @Autowired
    private IPersonRepository personRepository;

    @MockitoBean
    private IMilkCollectionFeignClient milkCollectionFeignClient;

    @Test
    @DisplayName("Debe mapear el DTO, guardar la persona en PostgreSQL y permitir consultarla")
    void createPerson_ShouldPersistPersonInDatabase() {
        // 1. Arrange (Preparar datos de entrada)
        PersonInDto dto = new PersonInDto();
        dto.setNames("Edward");
        dto.setLastNames("Tafur");
        dto.setIdentificationNumber("1085000111");
        dto.setBirthdate(LocalDate.of(1995, 8, 20));
        dto.setGender("M");
        dto.setEmail("edward@example.com");
        dto.setMobileNumber("3101234567");

        // 2. Act (Ejecutar el método a probar)
        personService.createPerson(dto);

        // 3. Assert (Verificar persistencia directa en PostgreSQL)
        Optional<Person> savedPersonOptional = personRepository.findByIdentificationNumber("1085000111");

        assertThat(savedPersonOptional).isPresent();

        Person savedPerson = savedPersonOptional.get();
        assertThat(savedPerson.getPersonId()).isNotNull(); // Valida generación de ID
        assertThat(savedPerson.getNames()).isEqualTo("Edward");
        assertThat(savedPerson.getLastNames()).isEqualTo("Tafur");
        assertThat(savedPerson.getEmail()).isEqualTo("edward@example.com");
        assertThat(savedPerson.getGender()).isEqualTo("M");
        assertThat(savedPerson.getBirthdate()).isEqualTo(LocalDate.of(1995, 8, 20));
    }

}
