package com.module.Milk_Collection.services.client;

import com.module.Common.dtos.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "Financial-Management", url= "http://financial-management:8280", fallback = FinancialManagementFallback.class)
@FeignClient(name = "Financial-Management", fallback = FinancialManagementFallback.class)
public interface IFinancialManagementFeingClient {

    @GetMapping(value = "/FinancialManagementController/getGreeting", produces = "application/json")
    public String getGreeting(@RequestHeader("milk-plant-correlation-id") String correlationId);

    @DeleteMapping(value = "/MilkSupplierPaymentController/deleteAllMilkSupplierPaymentByMilkSupplierId", produces = "application/json")
    public ResponseEntity<ResponseDto> deleteAllMilkSupplierPaymentByMilkSupplierId(@RequestParam("milkSupplierId") Long milkSupplierId);

}
