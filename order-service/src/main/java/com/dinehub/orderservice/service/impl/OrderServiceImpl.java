package com.dinehub.orderservice.service.impl;

import com.dinehub.orderservice.client.FoodClient;
import com.dinehub.orderservice.client.NotificationClient;
import com.dinehub.orderservice.dto.FoodDetails;
import com.dinehub.orderservice.dto.Notification;
import com.dinehub.orderservice.entity.Items;
import com.dinehub.orderservice.entity.Order;
import com.dinehub.orderservice.enums.NotificationChannel;
import com.dinehub.orderservice.enums.OrderStatus;
import com.dinehub.orderservice.exception.FoodNotAvailableException;
import com.dinehub.orderservice.exception.OrderNotFoundException;
import com.dinehub.orderservice.repository.OrderServiceRepository;
import com.dinehub.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderServiceRepository orderServiceRepository;

    private final FoodClient foodClient;

    private final NotificationClient notificationClient;


    @Override
    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Items item : order.getItems()) {
            FoodDetails foodDetails = foodClient.getFood(item.getFoodId());
            if(foodDetails.getCountAvailable()<item.getQuantity()){
                throw new FoodNotAvailableException(foodDetails.getName()+" is not available.");
            }
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void confirmOrder(Long orderId) {

        Order existingOrder = getOrderById(orderId);

        for (Items item : existingOrder.getItems()) {
            FoodDetails foodDetails = foodClient.getFood(item.getFoodId());
            if (foodDetails.getCountAvailable() < item.getQuantity()) {
                throw new FoodNotAvailableException(foodDetails.getName() + " is not available.");
            }
        }

        for (Items item : existingOrder.getItems()) {
            FoodDetails foodDetails = foodClient.getFood(item.getFoodId());
            foodClient.updateFoodCount(foodDetails.getId(),foodDetails.getCountAvailable()- item.getQuantity());
        }

        existingOrder.setUpdatedAt(LocalDateTime.now());
        existingOrder.setOrderStatus(OrderStatus.CONFIRMED);

        Order savedOrder = orderServiceRepository.save(existingOrder);
    }

    @Override
    public Order updateOrder(Long orderId, Order order) {

        Order existingOrder = getOrderById(orderId);

        existingOrder.setUpdatedAt(LocalDateTime.now());
        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setOrderType(order.getOrderType());
        existingOrder.setItems(order.getItems());

        Order savedOrder = orderServiceRepository.save(existingOrder);

        String title = switch (savedOrder.getOrderStatus()) {

            case CONFIRMED ->
                    "Your order #" + savedOrder.getOrderId()
                            + " has been confirmed by the restaurant and will be prepared shortly.";

            case PREPARING ->
                    "Your order #" + savedOrder.getOrderId()
                            + " is now being prepared by the restaurant.";

            case OUT_FOR_DELIVERY ->
                    "Your order #" + savedOrder.getOrderId()
                            + " is out for delivery and will reach you soon.";

            case DELIVERED ->
                    "Your order #" + savedOrder.getOrderId()
                            + " has been delivered successfully. Thank you for ordering with DineHub.";

            case CANCELLED ->
                    "Your order #" + savedOrder.getOrderId()
                            + " has been cancelled.";
        };

        Notification notification = Notification.builder()
                .userId(savedOrder.getUserId())
                .orderId(savedOrder.getOrderId())
                .notificationChannel(NotificationChannel.EMAIL)
                .title(title)
                .build();

        notificationClient.createNotification(notification);

        return savedOrder;
    }

    @Override
    public void deleteOrder(Long orderId) {
        getOrderById(orderId);
        orderServiceRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getOrderByUserId(Long userId) {
        List<Order> orders =  orderServiceRepository.findByUserIdOrderByCreatedAtDesc(userId);
        if(orders.isEmpty()) {
            throw new OrderNotFoundException("No order found for the userId "+userId);
        }
        return orders;
    }


}
