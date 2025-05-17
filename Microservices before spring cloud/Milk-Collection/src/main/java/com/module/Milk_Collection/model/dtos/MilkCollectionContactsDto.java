package com.module.Milk_Collection.model.dtos;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "milk-collection-contacts")
public record MilkCollectionContactsDto(String message, Map<String, String> contactDetails, List<Integer> onCallSupport) {
}
