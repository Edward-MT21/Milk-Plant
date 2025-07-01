package com.module.Financial_Management.model.dtos;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "financial-management-contacts")
public record FinancialManagementContactsDto(String message, Map<String, String> contactDetails, List<Integer> onCallSupport) {
}
