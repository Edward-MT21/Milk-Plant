package com.module.Common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkSupplierCollectionDTO {

    private Long milkSupplierId;
    private PersonOutDto personOutDto;
    private BigDecimal pricePerLiter;
    private List<MilkCollectionRecordDTO> collections;
    private Integer totalLitersMilk;

}
