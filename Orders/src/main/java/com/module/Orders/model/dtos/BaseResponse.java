package com.module.Orders.model.dtos;

public record BaseResponse(String[] errorMessages) {

    public boolean hasError(){
        return errorMessages != null && errorMessages.length > 0;
    }

}
