package com.dinehub.orderservice.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {

    @Id
    private Long id;

    @NotNull(message = "Food ID is required")
    @Positive(message = "Food ID must be greater than 0")
    private Long foodId;

    private String foodName;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}