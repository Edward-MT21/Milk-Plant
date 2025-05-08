package com.module.Financial_Management.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/FinancialManagementController")
public class FinancialManagementController {

    @GetMapping("/getGreeting")
    public String getGreeting() {
        return "Hello World since getGreeting FinancialManagement.";
    }

}
