package com.module.Milk_Collection.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PERSONS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;

    private String names;

    private String lastNames;

    private String identificationNumber;

    private Integer age;

    private String gender;

}
