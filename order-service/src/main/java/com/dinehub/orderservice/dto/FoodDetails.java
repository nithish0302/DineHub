package com.dinehub.orderservice.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FoodDetails {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer countAvailable;
}
