package com.persons.Persons.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "PERSONS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    private String names;

    private String lastNames;

    private String identificationNumber;

    private LocalDate birthdate;

    private String gender;

    private String email;

    private String mobileNumber;

}
