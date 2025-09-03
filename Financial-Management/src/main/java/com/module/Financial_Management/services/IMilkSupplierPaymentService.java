package com.module.Financial_Management.services;

import com.module.Financial_Management.model.dtos.InfoMilkSupplierPaymentDto;

import java.time.LocalDate;
import java.util.List;

public interface IMilkSupplierPaymentService {

    List<InfoMilkSupplierPaymentDto> getBiweeklyInfoMilkSupplierPayment(LocalDate startDate, LocalDate endDate);

    boolean deleteMilkSupplierPaymentById(Long milkSupplierPaymentId);

    boolean deleteAllMilkSupplierPaymentByMilkSupplierId(Long milkSupplierId);
}
