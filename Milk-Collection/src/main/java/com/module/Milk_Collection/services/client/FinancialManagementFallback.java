package com.module.Milk_Collection.services.client;

import com.module.Common.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FinancialManagementFallback implements IFinancialManagementFeingClient{

    @Override
    public String getGreeting(String correlationId) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> deleteAllMilkSupplierPaymentByMilkSupplierId(Long milkSupplierId) {
        return null;
    }
}
