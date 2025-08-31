package com.module.Milk_Collection.services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "Financial-Management", url= "http://financial-management:8280", fallback = FinancialManagementFallback.class)
@FeignClient(name = "Financial-Management", fallback = FinancialManagementFallback.class)
public interface IFinancialManagementFeingClient {

    @GetMapping(value = "/FinancialManagementController/getGreeting", produces = "application/json")
    public String getGreeting(@RequestHeader("milk-plant-correlation-id") String correlationId);

}
