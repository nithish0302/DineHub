package com.dinehub.foodService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseEntity<String> handleFoodNotFound(
            FoodNotFoundException exception) {

        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}