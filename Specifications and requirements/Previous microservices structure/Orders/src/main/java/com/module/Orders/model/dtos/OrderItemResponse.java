package com.module.Orders.model.dtos;

public record OrderItemResponse(
         Long idOrderItems,
         String sku,
         Double price,
         Long quantity) {
}
