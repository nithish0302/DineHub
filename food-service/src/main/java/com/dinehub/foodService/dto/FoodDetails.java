package com.dinehub.foodService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class FoodDetails {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long countAvailable;
}
