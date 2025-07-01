package com.persons.Persons.model.dtos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "persons-contacts")
@Getter
@Setter
public class PersonsContactsDto {
    private String message;
    private Map<String, String> contactDetails;
    private List<Integer> onCallSupport;
}
