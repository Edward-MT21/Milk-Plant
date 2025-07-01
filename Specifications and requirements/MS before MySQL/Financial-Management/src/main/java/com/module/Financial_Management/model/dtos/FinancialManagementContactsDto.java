package com.module.Financial_Management.model.dtos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "financial-management-contacts")
@Getter
@Setter
public class FinancialManagementContactsDto {
    private String message;
    private Map<String, String> contactDetails;
    private List<Integer> onCallSupport;
}
