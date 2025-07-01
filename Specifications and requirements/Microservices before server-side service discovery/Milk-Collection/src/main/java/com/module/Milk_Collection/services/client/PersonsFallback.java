package com.module.Milk_Collection.services.client;

import com.module.Milk_Collection.model.dtos.PersonOutDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonsFallback implements IPersonsFeingClient {

    @Override
    public ResponseEntity<PersonOutDto> fetchPersonById(String correlationId, Long personId) {
        return null;
    }

}
