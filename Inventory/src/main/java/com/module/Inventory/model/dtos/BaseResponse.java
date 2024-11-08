package com.module.Milk_Collection_Clon.model.dtos;

public record BaseResponse(String[] errorMessages) {

    public boolean hasError(){
        return errorMessages != null && errorMessages.length > 0;
    }

}
