package com.module.Milk_Collection.services.client;

import com.module.Milk_Collection.model.dtos.PersonOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "Persons", url= "http://persons:8180", fallback = PersonsFallback.class)
@FeignClient(name = "Persons", fallback = PersonsFallback.class)
public interface IPersonsFeingClient {

    @GetMapping(value = "/PersonController/fetchPersonById", produces = "application/json")
    public ResponseEntity<PersonOutDto> fetchPersonById(
            @RequestHeader("milk-plant-correlation-id") String correlationId, @RequestParam("personId") Long personId);

}
