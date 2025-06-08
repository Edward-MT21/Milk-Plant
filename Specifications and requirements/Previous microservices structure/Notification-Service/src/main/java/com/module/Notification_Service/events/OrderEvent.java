package com.module.Notification_Service.events;

import com.module.Notification_Service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
