package com.dinehub.orderservice.exception;

import com.dinehub.orderservice.entity.Order;

public class FoodNotAvailableException extends RuntimeException {
    public FoodNotAvailableException(String s) {
        super(s);
    }
}
