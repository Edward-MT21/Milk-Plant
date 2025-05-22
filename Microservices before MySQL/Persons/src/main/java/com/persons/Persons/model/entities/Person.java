package com.persons.Persons.model.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private Integer age;

    private String gender;

    private String email;

    private String mobileNumber;

}
