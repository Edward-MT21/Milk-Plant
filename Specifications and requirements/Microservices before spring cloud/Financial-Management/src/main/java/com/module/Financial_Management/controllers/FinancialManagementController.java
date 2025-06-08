package com.module.Financial_Management.controllers;

import com.module.Financial_Management.model.dtos.FinancialManagementContactsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/FinancialManagementController")
public class FinancialManagementController {


    //
    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private FinancialManagementContactsDto financialManagementContactsDto;


    @GetMapping("/getGreeting")
    public String getGreeting() {
        return "Hello World since getGreeting FinancialManagement.";
    }

    @GetMapping("/getBuildVersion")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @GetMapping("/get-java-home")
    public ResponseEntity<String> getJavaHome() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/get-financial-management-contacts")
    public ResponseEntity<FinancialManagementContactsDto> getFinancialManagementContacts() {
        return ResponseEntity.status(HttpStatus.OK).body(financialManagementContactsDto);
    }

}
