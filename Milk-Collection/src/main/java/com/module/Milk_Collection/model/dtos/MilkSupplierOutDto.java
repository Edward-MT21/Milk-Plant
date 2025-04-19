package com.module.Milk_Collection.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilkSupplierOutDto {

    private Long milkSupplierId;

    private Long personId;
}
