package com.module.Milk_Collection.services.client;

import com.module.Milk_Collection.model.dtos.PersonOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "Financial-Management", url= "http://financial-management:8280", fallback = FinancialManagementFallback.class)
@FeignClient(name = "Financial-Management", fallback = FinancialManagementFallback.class)
public interface IFinancialManagementFeingClient {

    @GetMapping(value = "/FinancialManagementController/getGreeting", produces = "application/json")
    public String getGreeting(@RequestHeader("milk-plant-correlation-id") String correlationId);

}
