package com.module.Milk_Collection.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkSupplierDetailsDto {

    private Long milkSupplierId;
    private PersonOutDto personOutDto;
    private String greetingFinancialManagement;

}
