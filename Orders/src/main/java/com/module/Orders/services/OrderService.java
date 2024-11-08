package com.module.Orders.services;

import com.module.Orders.model.dtos.*;
import com.module.Orders.model.entities.Order;
import com.module.Orders.model.entities.OrderItems;
import com.module.Orders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(@RequestBody OrderRequest orderRequest) {

        BaseResponse baseResponse = this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/InventoryController/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if(baseResponse != null && !baseResponse.hasError()) {

            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream()
                    .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                    .toList());
            this.orderRepository.save(order);
        }else{
            throw  new IllegalArgumentException("Some of the products are not in stock");
        }


    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();

        return orders.stream().map(this::mapOrderToOrderResponse).toList();
    }

    private OrderResponse mapOrderToOrderResponse(Order order) {
        return new OrderResponse(
                order.getIdOrder(),
                order.getOrderNumber(),
                order.getOrderItems().stream().map(this::mapToOrderItemResponse).toList());
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItems orderItems) {

        return new OrderItemResponse(
                orderItems.getIdOrderItems(),
                orderItems.getSku(),
                orderItems.getPrice(),
                orderItems.getQuantity());

    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {

        return OrderItems.builder()
                .idOrderItems(orderItemRequest.getIdOrderItems())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }

}
