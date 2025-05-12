package com.module.Financial_Management.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/FinancialManagementController")
public class FinancialManagementController {

    @Value("${build.version}")
    private String buildVersion;


    @GetMapping("/getGreeting")
    public String getGreeting() {
        return "Hello World since getGreeting FinancialManagement.";
    }

    @GetMapping("/getBuildVersion")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

}
