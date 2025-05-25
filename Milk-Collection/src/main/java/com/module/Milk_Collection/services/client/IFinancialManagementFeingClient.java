package com.module.Milk_Collection.services.client;

import com.module.Milk_Collection.model.dtos.PersonOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Financial-Management")
public interface IFinancialManagementFeingClient {

    @GetMapping(value = "/FinancialManagementController/getGreeting", produces = "application/json")
    public String getGreeting();

}
