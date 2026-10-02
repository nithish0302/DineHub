package com.dinehub.orderservice.service;

import com.dinehub.orderservice.entity.Order;
import jakarta.validation.Valid;


public interface OrderService {
    public Order createOrder(@Valid Order order);

    public Order getOrderById(Long orderId);

    Order updateOrder(Long orderId, @Valid Order order);

    void deleteOrder(Long orderId);
}
