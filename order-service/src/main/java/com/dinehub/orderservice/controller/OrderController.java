package com.dinehub.orderservice.controller;

import com.dinehub.orderservice.entity.Order;
import com.dinehub.orderservice.service.OrderService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order){

        return new ResponseEntity<>(
                orderService.createOrder(order),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId){
        return ResponseEntity.ok(
                orderService.getOrderById(orderId)
                );
    }

    @GetMapping("/confirm/{orderId}")
    public ResponseEntity<Order> confirmOrder(@PathVariable Long orderId){
        orderService.confirmOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId,@Valid @RequestBody Order order){
        return ResponseEntity.ok(
                orderService.updateOrder(orderId,order)
        );
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getUserId/{userId}")
    public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(
                orderService
                        .getOrderByUserId(userId)
        );
    }
}
