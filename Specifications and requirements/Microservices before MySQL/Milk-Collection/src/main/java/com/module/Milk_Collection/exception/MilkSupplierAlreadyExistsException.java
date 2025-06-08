package com.module.Milk_Collection.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MilkSupplierAlreadyExistsException extends RuntimeException {

    public MilkSupplierAlreadyExistsException(String message) {
        super(message);
    }

}
