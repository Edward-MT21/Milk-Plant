package com.persons.Persons.model.dtos;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "persons-contacts")
public record PersonsContactsDto(String message, Map<String, String> contactDetails, List<Integer> onCallSupport) {
}
