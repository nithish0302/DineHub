package com.dinehub.orderservice.entity;

import com.dinehub.orderservice.enums.OrderStatus;
import com.dinehub.orderservice.enums.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Long orderId;

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Long userId;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<Items> items = new ArrayList<>();

    @DecimalMin(value = "0.0", inclusive = true,
            message = "Total price cannot be negative")
    private BigDecimal totalPrice;

    @NotNull(message = "Order type is required")
    private OrderType orderType;

    private OrderStatus orderStatus;

    @PastOrPresent(message = "createdAt must not be in future")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
