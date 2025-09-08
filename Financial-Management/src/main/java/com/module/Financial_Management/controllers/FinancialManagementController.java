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


    //
    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private FinancialManagementContactsDto financialManagementContactsDto;


    @GetMapping("/getGreeting")
    public String getGreeting(@RequestHeader("milk-plant-correlation-id") String correlationId) {
        logger.debug("getGreeting start and end");
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

    @DeleteMapping("/deleteAllMilkSupplierPaymentByMilkSupplierId")
    public ResponseEntity<ResponseDto> deleteAllMilkSupplierPaymentByMilkSupplierId(@RequestParam("milkSupplierId") Long milkSupplierId) {
        logger.debug("deleteAllMilkSupplierPaymentByMilkSupplierId start");

        boolean deleted = iMilkSupplierPaymentService.deleteAllMilkSupplierPaymentByMilkSupplierId(milkSupplierId);
        if (!deleted) {
            return ResponseEntity.badRequest().body(new ResponseDto("400", "MilkSupplierPayment not deleted"));
        }

        logger.debug("deleteAllMilkSupplierPaymentByMilkSupplierId end");
        return ResponseEntity.ok().body(new ResponseDto("200", "MilkSupplierPayment deleted successfully"));
    }

}
