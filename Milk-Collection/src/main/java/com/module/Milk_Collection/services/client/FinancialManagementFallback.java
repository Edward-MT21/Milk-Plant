package com.module.Milk_Collection.services.client;

import com.module.Common.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FinancialManagementFallback implements IFinancialManagementFeingClient{

    @Override
    public String getGreeting(String correlationId) {
        return "An error occurred in Financial-Management service";
    }

    @Override
    public ResponseEntity<ResponseDto> deleteAllMilkSupplierPaymentByMilkSupplierId(Long milkSupplierId) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseDto("503", "An error occurred in Financial-Management service"));
    }

}
