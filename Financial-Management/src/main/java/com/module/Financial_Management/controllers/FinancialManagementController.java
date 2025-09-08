package com.module.Financial_Management.controllers;

import com.module.Common.dtos.ResponseDto;
import com.module.Financial_Management.model.dtos.FinancialManagementContactsDto;
import com.module.Financial_Management.services.IMilkSupplierPaymentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/FinancialManagementController", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class FinancialManagementController {

    private static final Logger logger = LoggerFactory.getLogger(FinancialManagementController.class);
    private final IMilkSupplierPaymentService iMilkSupplierPaymentService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private FinancialManagementContactsDto financialManagementContactsDto;


    @GetMapping("/getGreeting")
    public String getGreeting(@RequestHeader("milk-plant-correlation-id") String correlationId) {
        logger.debug("Start getGreeting");

        logger.debug("End getGreeting");
        return "Hello World since getGreeting FinancialManagement.";
    }

    @GetMapping("/getBuildVersion")
    public ResponseEntity<String> getBuildVersion() {
        logger.debug("Start getBuildVersion");

        logger.debug("End getBuildVersion");
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @GetMapping("/getJavaHome")
    public ResponseEntity<String> getJavaHome() {
        logger.debug("Start getJavaHome");

        logger.debug("End getJavaHome");
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/getFinancialManagementContacts")
    public ResponseEntity<FinancialManagementContactsDto> getFinancialManagementContacts() {
        logger.debug("Start getFinancialManagementContacts");

        logger.debug("End getFinancialManagementContacts");
        return ResponseEntity.status(HttpStatus.OK).body(financialManagementContactsDto);
    }

}
