package com.module.Milk_Collection.model.dtos;
import com.module.Common.dtos.PersonOutDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkSupplierDetailsDto {

    private Long milkSupplierId;
    private PersonOutDto personOutDto;
    private String greetingFinancialManagement;
    private BigDecimal pricePerLiter;

}
