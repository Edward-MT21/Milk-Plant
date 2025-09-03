package com.module.Milk_Collection.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkCollectionDto {

    private Long milkCollectionId;
    private Long milkSupplierId;
    private Integer litersMilk;
    private LocalDate collectionDate;

}
