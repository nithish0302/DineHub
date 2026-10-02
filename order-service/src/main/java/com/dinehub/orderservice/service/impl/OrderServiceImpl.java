package com.dinehub.orderservice.service.impl;

import com.dinehub.orderservice.entity.Items;
import com.dinehub.orderservice.entity.Order;
import com.dinehub.orderservice.exception.OrderNotFoundException;
import com.dinehub.orderservice.repository.OrderServiceRepository;
import com.dinehub.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderServiceRepository orderServiceRepository;

    @Override
    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Items item : order.getItems()) {

            BigDecimal subTotal = item.getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setSubtotal(subTotal);
            totalPrice = totalPrice.add(subTotal);
        }
        order.setTotalPrice(totalPrice);
        return orderServiceRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderServiceRepository.findById(orderId)
                .orElseThrow(()->
                        new OrderNotFoundException("order not found with "+orderId))
                ;
    }

    @Override
    public Order updateOrder(Long orderId, Order order) {
        Order existingOrder = getOrderById(orderId);
        existingOrder.setUpdatedAt(LocalDateTime.now());
        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setOrderType(order.getOrderType());
        existingOrder.setItems(order.getItems());
        return orderServiceRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        getOrderById(orderId);
        orderServiceRepository.deleteById(orderId);
    }


}
