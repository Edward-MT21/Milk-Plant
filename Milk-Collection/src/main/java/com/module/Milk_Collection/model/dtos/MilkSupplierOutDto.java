package com.module.Milk_Collection.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "MilkSupplierOutDto",
        description = "Schema to MilkSupplierOutDto information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilkSupplierOutDto {

    private Long milkSupplierId;

    private Long personId;
}
