package com.module.Orders.model.dtos;

import java.util.List;


public record OrderResponse(
        Long idOrder,
        String orderNumber,
        List<OrderItemResponse> orderItemResponse) {
}
