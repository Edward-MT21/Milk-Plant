package com.module.Milk_Collection.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkCollectionDetailsDto {
    private Long milkCollectionId;
    private MilkSupplierDetailsDto milkSupplierDetailsDto;
    private Integer litersMilk;
}
