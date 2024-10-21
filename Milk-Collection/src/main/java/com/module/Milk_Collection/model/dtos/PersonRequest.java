package com.module.Milk_Collection.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRequest {

    private Long idPerson;

    private String names;

    private String lastNames;

    private String identificationNumber;

    private Integer age;

    private String gender;

}
