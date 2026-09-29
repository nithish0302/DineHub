package com.dinehub.foodService.exception;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException(String message) {
        super(message);
    }
}
