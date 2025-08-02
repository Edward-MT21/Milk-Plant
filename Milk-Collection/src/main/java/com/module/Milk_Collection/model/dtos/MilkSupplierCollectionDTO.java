package com.module.Milk_Collection.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkSupplierCollectionDTO {

    private Long milkSupplierId;
    private PersonOutDto personOutDto;
    private List<MilkCollectionRecordDTO> collections;

}
