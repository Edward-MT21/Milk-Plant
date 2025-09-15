package com.module.Milk_Collection.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(
        name = "MilkSupplierInDto",
        description = "Schema to MilkSupplierInDto information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilkSupplierInDto {

    private Long milkSupplierId;
    private Long personId;
    private BigDecimal pricePerLiter;
}
