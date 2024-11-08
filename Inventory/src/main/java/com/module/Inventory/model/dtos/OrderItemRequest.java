package com.module.Milk_Collection_Clon.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    private Long idOrderItems;

    private String sku;

    private Double price;

    private Long quantity;

}
