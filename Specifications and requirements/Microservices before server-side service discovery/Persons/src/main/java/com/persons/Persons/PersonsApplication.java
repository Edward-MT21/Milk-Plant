package com.persons.Persons;

import com.persons.Persons.model.dtos.PersonsContactsDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value = { PersonsContactsDto.class })
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class PersonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonsApplication.class, args);
	}

}
